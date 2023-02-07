package webservice.controllers;

import org.springframework.web.bind.annotation.*;
import webservice.contracts.MonitoredEndpointInputContract;
import webservice.contracts.MonitoredEndpointViewContract;
import webservice.services.MonitoredEndpointService;


@RestController
@RequestMapping("monitoredEndpoint")
public class MonitoredEndpointController {
    public MonitoredEndpointController(MonitoredEndpointService monitoredEndpointService) {
        this.monitoredEndpointService = monitoredEndpointService;
    }

    private MonitoredEndpointService monitoredEndpointService;

    @GetMapping("list")
    public Iterable<MonitoredEndpointViewContract> getAll() {
        return monitoredEndpointService.getAll();
    }

    @PostMapping
    public MonitoredEndpointViewContract create(@RequestBody MonitoredEndpointInputContract contract) {
        return monitoredEndpointService.save(contract);
    }

    @PutMapping("{id}")
    public MonitoredEndpointViewContract edit(@PathVariable String id, @RequestBody MonitoredEndpointInputContract editionContract) {
        return monitoredEndpointService.edit(id, editionContract);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        monitoredEndpointService.delete(id);
    }
}
