package com.sochanski.location;

import com.sochanski.ApiUtils;
import com.sochanski.container.Container;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = ApiUtils.BASE_API_PATH + "/location")
@CrossOrigin
public class LocationController {

    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Location> getAllLocations() {
        return service.getAllLocations();
    }

    @PreAuthorize(value ="hasAuthority('SETTINGS')")
    @PostMapping
    public Location createLocation(@RequestBody @Valid LocationParameters params) {
        return service.createLocation(params);
    }

    @GetMapping(path = "/{locationId}/containers")
    public List<Container> getLocationContainers(@PathVariable long locationId) {
        return service.getLocationContainers(locationId);
    }

}
