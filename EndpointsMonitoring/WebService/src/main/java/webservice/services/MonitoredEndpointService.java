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

    public MonitoredEndpointService(MonitoredEndpointRepository monitoredEndpointRepository) {
        this.monitoredEndpointRepository = monitoredEndpointRepository;
    }

    public List<MonitoredEndpointViewContract> getAll() {
        return StreamSupport.stream(monitoredEndpointRepository.findAll().spliterator(), false)
                .map(x -> convert(x))
                .toList();
    }

    public MonitoredEndpointViewContract save(MonitoredEndpointInputContract contract) {
        //todo add user
        var entity = new MonitoredEndpoint(contract.getName(), contract.getUrl(), LocalDateTime.now(), contract.getMonitoredInterval(), null);
        var savedEntity = monitoredEndpointRepository.save(entity);
        return convert(savedEntity);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public MonitoredEndpointViewContract edit(String id, MonitoredEndpointInputContract editionContract) {
        var originalEntity = monitoredEndpointRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Monitored endpoint with id %s does not exists", id)));
        var changedEntity = originalEntity.edit(editionContract.getName(), editionContract.getUrl(), editionContract.getMonitoredInterval());
        var savedEntity = monitoredEndpointRepository.save(changedEntity);
        return convert(savedEntity);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void delete(String id) {
        if (!monitoredEndpointRepository.existsById(id)) {
            throw new NoSuchElementException(String.format("Monitored endpoint with id %s does not exists", id));
        }
        monitoredEndpointRepository.deleteById(id);
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
