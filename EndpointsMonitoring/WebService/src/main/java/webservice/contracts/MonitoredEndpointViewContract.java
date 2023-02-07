package webservice.contracts;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class MonitoredEndpointViewContract {
    private String id;
    private String name;
    private String url;
    private LocalDateTime dateOfCreation;
    private int monitoredInterval;
}

