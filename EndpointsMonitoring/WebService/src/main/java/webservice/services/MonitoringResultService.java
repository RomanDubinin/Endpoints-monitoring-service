package webservice.services;

import domain.contracts.MonitoringResult;
import domain.repositories.MonitoringResultRepository;
import org.springframework.stereotype.Service;
import webservice.contracts.MonitoringResultViewContract;

import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class MonitoringResultService {
    private MonitoringResultRepository monitoringResultRepository;
    private MonitoredEndpointService monitoredEndpointService;

    public MonitoringResultService(MonitoringResultRepository monitoringResultRepository, MonitoredEndpointService monitoredEndpointService) {
        this.monitoringResultRepository = monitoringResultRepository;
        this.monitoredEndpointService = monitoredEndpointService;
    }

    public List<MonitoringResultViewContract> getLatest10(String monitoredEndpointId) {
        if (!monitoredEndpointService.exists(monitoredEndpointId)) {
            return Collections.emptyList();
        }
        return StreamSupport.stream(monitoringResultRepository.findLatest(monitoredEndpointId, 10).spliterator(), false)
                .map(x -> convert(x))
                .toList();
    }

    private MonitoringResultViewContract convert(MonitoringResult monitoringResult) {
        return MonitoringResultViewContract
                .builder()
                .dateOfCheck(monitoringResult.getDateOfCheck())
                .httpStatus(monitoringResult.getHttpStatus())
                .returnedPayload(monitoringResult.getReturnedPayload())
                .build();
    }
}
