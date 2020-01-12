package com.sochanski.container;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ContainerNotFoundException extends ResponseStatusException {
    public ContainerNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Container not found");
    }
}
