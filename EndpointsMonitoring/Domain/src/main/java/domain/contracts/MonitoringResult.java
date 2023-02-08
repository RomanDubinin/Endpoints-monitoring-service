package domain.contracts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class MonitoringResult {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    //todo: change type to UUID
    private String id;

    private LocalDateTime dateOfCheck;
    private int httpStatus;
    private String returnedPayload;
    @ManyToOne(targetEntity = MonitoredEndpoint.class)
    @JoinColumn(name = "monitored_endpoint_id")
    private MonitoredEndpoint monitoredEndpoint;

    public MonitoringResult(LocalDateTime dateOfCheck, int httpStatus, String returnedPayload, MonitoredEndpoint monitoredEndpoint) {
        this.dateOfCheck = dateOfCheck;
        this.httpStatus = httpStatus;
        this.returnedPayload = returnedPayload;
        this.monitoredEndpoint = monitoredEndpoint;
    }
}
