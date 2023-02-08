package domain.repositories;

import domain.contracts.MonitoringResult;
import org.springframework.data.repository.CrudRepository;

public interface MonitoringResultRepository extends CrudRepository<MonitoringResult, String> {
}
