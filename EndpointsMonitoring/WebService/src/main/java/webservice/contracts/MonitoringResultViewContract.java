package webservice.contracts;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class MonitoringResultViewContract {
    private LocalDateTime dateOfCheck;
    private int httpStatus;
    private String returnedPayload;
}
