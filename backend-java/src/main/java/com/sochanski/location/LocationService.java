package com.sochanski.location;

import com.sochanski.container.Container;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    public List<Location> getAllLocations() {
        return repository.findAll();
    }

    public Location createLocation(LocationParameters params) {
        return repository.save(new Location(params));
    }

    public List<Container> getLocationContainers(long locationId) {
        return repository.findById(locationId)
                .map(Location::getContainers)
                .orElseThrow(LocationNotFoundException::new);
    }
}
