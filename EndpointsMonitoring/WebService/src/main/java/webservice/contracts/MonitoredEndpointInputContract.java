package webservice.contracts;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonitoredEndpointInputContract {
    private String name;
    // probably there shouldn't be opportunity to change url of existing monitored endpoint.
    @Pattern(regexp="^(http|https):\\/\\/[^ \"]+$", message="value should be valid URL")
    private String url;
    @Min(value = 1, message = "value should be positive integer")
    private int monitoredInterval;
}
