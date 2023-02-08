package webservice.services;

import domain.contracts.MonitoredEndpoint;
import domain.repositories.MonitoredEndpointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import webservice.contracts.MonitoredEndpointInputContract;
import webservice.contracts.MonitoredEndpointViewContract;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

@Service
public class MonitoredEndpointService {
    private MonitoredEndpointRepository monitoredEndpointRepository;
    private UserService userService;
    private MonitoringService monitoringService;

    public MonitoredEndpointService(MonitoredEndpointRepository monitoredEndpointRepository, UserService userService, MonitoringService monitoringService) {
        this.monitoredEndpointRepository = monitoredEndpointRepository;
        this.userService = userService;
        this.monitoringService = monitoringService;
    }

    public List<MonitoredEndpointViewContract> getAll() {
        var user = userService.getCurrentlyAuthorisedUser();
        return StreamSupport.stream(monitoredEndpointRepository.findAllByOwnerId(user.getId()).spliterator(), false)
                .map(x -> convert(x))
                .toList();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public MonitoredEndpointViewContract save(MonitoredEndpointInputContract contract) {
        var user = userService.getCurrentlyAuthorisedUser();
        var entity = new MonitoredEndpoint(contract.getName(), contract.getUrl(), LocalDateTime.now(), contract.getMonitoredInterval(), user);
        var savedEntity = monitoredEndpointRepository.save(entity);
        monitoringService.addToMonitoring(entity);
        return convert(savedEntity);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public MonitoredEndpointViewContract edit(String id, MonitoredEndpointInputContract editionContract) {
        var user = userService.getCurrentlyAuthorisedUser();
        var originalEntity = monitoredEndpointRepository.findByIdAndOwnerId(id, user.getId()).orElseThrow(() -> new NoSuchElementException(String.format("Monitored endpoint with id %s does not exists", id)));
        var changedEntity = originalEntity.edit(editionContract.getName(), editionContract.getUrl(), editionContract.getMonitoredInterval());
        var savedEntity = monitoredEndpointRepository.save(changedEntity);
        monitoringService.deleteFromMonitoring(originalEntity.getId());
        monitoringService.addToMonitoring(savedEntity);
        return convert(savedEntity);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void delete(String id) {
        var user = userService.getCurrentlyAuthorisedUser();
        if (!monitoredEndpointRepository.existsByIdAndOwnerId(id, user.getId())) {
            throw new NoSuchElementException(String.format("Monitored endpoint with id %s does not exists", id));
        }
        monitoredEndpointRepository.deleteByIdAndOwnerId(id, user.getId());
        monitoringService.deleteFromMonitoring(id);
    }

    private MonitoredEndpointViewContract convert(MonitoredEndpoint monitoredEndpoint) {
        return MonitoredEndpointViewContract
                .builder()
                .id(monitoredEndpoint.getId())
                .name(monitoredEndpoint.getName())
                .url(monitoredEndpoint.getUrl())
                .dateOfCreation(monitoredEndpoint.getDateOfCreation())
                .monitoredInterval(monitoredEndpoint.getMonitoredInterval())
                .build();
    }
}
