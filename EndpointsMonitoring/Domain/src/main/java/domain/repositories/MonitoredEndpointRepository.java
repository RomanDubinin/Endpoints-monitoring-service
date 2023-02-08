package domain.repositories;

import domain.contracts.MonitoredEndpoint;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface MonitoredEndpointRepository extends CrudRepository<MonitoredEndpoint, String> {
    Iterable<MonitoredEndpoint> findAllByOwnerId(String ownerId);
    Optional<MonitoredEndpoint> findByIdAndOwnerId(String id, String ownerId);
    boolean existsByIdAndOwnerId(String id, String ownerId);
    void deleteByIdAndOwnerId(String id, String ownerId);
}
