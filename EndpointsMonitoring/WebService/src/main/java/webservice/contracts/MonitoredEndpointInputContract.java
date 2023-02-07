package webservice.contracts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonitoredEndpointInputContract {
    private String name;
    private String url;
    private int monitoredInterval;
}
