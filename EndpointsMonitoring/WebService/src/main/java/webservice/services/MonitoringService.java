package webservice.services;

import domain.contracts.MonitoredEndpoint;
import domain.contracts.MonitoringResult;
import domain.repositories.MonitoredEndpointRepository;
import domain.repositories.MonitoringResultRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import webmonitoring.url.UrlChecker;

import java.io.IOException;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MonitoringService {
    private Logger logger = Logger.getLogger(String.valueOf(MonitoringService.class));
    private ScheduledExecutorService scheduler;
    private ExecutorService executor;
    private MonitoredEndpointRepository monitoredEndpointRepository;
    private MonitoringResultRepository monitoringResultRepository;
    private UrlChecker urlChecker;
    private Map<String, ScheduledFuture<?>> scheduledMonitors;

    public MonitoringService(
            @Value("${scheduledThreadPoolSize}") int scheduledThreadPoolSize,
            @Value("${fixedThreadPoolSize}") int fixedThreadPoolSize,
            MonitoredEndpointRepository monitoredEndpointRepository,
            MonitoringResultRepository monitoringResultRepository,
            UrlChecker urlChecker) {
        this.scheduler = Executors.newScheduledThreadPool(scheduledThreadPoolSize);
        this.executor = Executors.newFixedThreadPool(fixedThreadPoolSize);
        this.scheduledMonitors = new HashMap<>();
        this.monitoredEndpointRepository = monitoredEndpointRepository;
        this.monitoringResultRepository = monitoringResultRepository;
        this.urlChecker = urlChecker;
    }

    // Short remark why there is executor.execute(() -> ... ) and what does it mean to us:
    // Unfortunately if task started under scheduleAtFixedRate takes more than period - next iteration will start later than you would expect by the schedule.
    // So, having only call to executor.execute we are making our scheduled task as short as possible - just starting new thread from pool.
    // Maybe it is not so needed - intervals are usually not so short or for us, it is fine to have url monitored less often if there is performance issue on url owner side...
    // Anyway I would discuss it with the team/customer what is desired behaviour before adding second thread pool :)
    public void addToMonitoring(MonitoredEndpoint endpoint){
        var monitor = scheduler.scheduleAtFixedRate(() -> {
            executor.execute(() -> executeMonitoringCheck(endpoint));
        }, 0, endpoint.getMonitoredInterval(), TimeUnit.SECONDS);
        scheduledMonitors.put(endpoint.getId(), monitor);
    }

    public void deleteFromMonitoring(String endpointId) {
        var scheduledMonitor = scheduledMonitors.get(endpointId);
        scheduledMonitor.cancel(false);
        scheduledMonitors.remove(endpointId);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startExecutionOfStoredMonitoredEndpoints() {
        var monitoredEndpoints = monitoredEndpointRepository.findAll();
        for (var endpoint : monitoredEndpoints) {
            addToMonitoring(endpoint);
        }
    }

    private void executeMonitoringCheck(MonitoredEndpoint endpoint) {
        try {
            var checkResult = urlChecker.check(endpoint.getUrl());
            var monitoringResult = new MonitoringResult(LocalDateTime.now(), checkResult.getStatusCode(), checkResult.getBody(), endpoint);
            monitoringResultRepository.save(monitoringResult);
        } catch (ConnectException e) {
            logger.log(Level.WARNING, String.format("Connection failure to endpoint with id %s. Url: %s", endpoint.getId(), endpoint.getUrl()));
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, String.format("Error while retrieving endpoint with id %s. Url: %s", endpoint.getId(), endpoint.getUrl()));
            throw new RuntimeException(e);
        }
    }
}
