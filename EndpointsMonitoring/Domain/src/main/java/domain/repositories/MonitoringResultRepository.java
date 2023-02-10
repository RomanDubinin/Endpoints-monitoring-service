package domain.repositories;

import domain.contracts.MonitoringResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MonitoringResultRepository extends CrudRepository<MonitoringResult, String> {
    @Query(value = "select * \n" +
            "from monitoring_result mr\n" +
            "where mr.monitored_endpoint_id = :monitoredEndpointId\n" +
            "order by mr.date_of_check desc\n" +
            "limit :count", nativeQuery = true)
    Iterable<MonitoringResult> findLatest(@Param("monitoredEndpointId") String monitoredEndpointId, @Param("count") int count);
}
