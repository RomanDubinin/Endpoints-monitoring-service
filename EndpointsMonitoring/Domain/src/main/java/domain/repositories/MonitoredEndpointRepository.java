package domain.repositories;

import domain.contracts.MonitoredEndpoint;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MonitoredEndpointRepository extends CrudRepository<MonitoredEndpoint, String> {
}
