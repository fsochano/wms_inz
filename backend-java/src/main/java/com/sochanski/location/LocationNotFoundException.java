package com.sochanski.location;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LocationNotFoundException extends ResponseStatusException {
    public LocationNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Location not found");
    }
}
