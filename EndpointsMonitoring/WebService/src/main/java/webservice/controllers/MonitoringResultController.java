package webservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webservice.contracts.MonitoringResultViewContract;
import webservice.services.MonitoringResultService;

import java.util.List;

@RestController
@RequestMapping("monitoringResult")
public class MonitoringResultController {
    private MonitoringResultService monitoringResultService;

    public MonitoringResultController(MonitoringResultService monitoringResultService) {
        this.monitoringResultService = monitoringResultService;
    }

    @GetMapping("listLatest10/{monitoredEndpointId}")
    public List<MonitoringResultViewContract> listLatest10(@PathVariable String monitoredEndpointId) {
        return monitoringResultService.getLatest10(monitoredEndpointId);
    }
}
