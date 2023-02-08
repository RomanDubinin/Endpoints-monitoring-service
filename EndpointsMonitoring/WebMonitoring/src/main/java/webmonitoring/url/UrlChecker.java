package webmonitoring.url;

import webmonitoring.contracts.url.UrlCheckResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class UrlChecker {
    private HttpClient httpClient;

    public UrlChecker() {
        this.httpClient = HttpClient.newBuilder().build();
    }

    public UrlCheckResult check(String url) throws IOException, InterruptedException {
        var uri = URI.create(url);

        var request = HttpRequest
                .newBuilder()
                .uri(uri)
                .GET()
                .build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return UrlCheckResult
                .builder()
                .statusCode(response.statusCode())
                .body(response.body())
                .build();
    }
}
