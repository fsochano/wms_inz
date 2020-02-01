package com.sochanski.container;

import com.sochanski.ApiUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = ApiUtils.BASE_API_PATH + "/container")
@CrossOrigin
public class ContainerController {

    private final ContainerService service;

    public ContainerController(ContainerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Container> getAllContainers() {
        return service.getAllContainers();
    }

    @PreAuthorize(value ="hasAuthority('SETTINGS')")
    @PostMapping
    public Container createContainer(@RequestBody @Valid ContainerParameters params) {
        return service.createContainer(params);
    }

    @DeleteMapping(path = "/{containerId}")
    public void deleteContainer(@PathVariable long containerId) {
        service.deleteContainer(containerId);
    }

}
