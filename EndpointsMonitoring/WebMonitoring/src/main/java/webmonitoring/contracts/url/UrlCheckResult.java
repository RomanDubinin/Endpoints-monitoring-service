package webmonitoring.contracts.url;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UrlCheckResult {
    private int statusCode;
    private String body;
}
