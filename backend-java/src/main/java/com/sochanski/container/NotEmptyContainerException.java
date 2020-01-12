package com.sochanski.container;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotEmptyContainerException extends ResponseStatusException {
    public NotEmptyContainerException() {
        super(HttpStatus.FORBIDDEN, "Container not empty");
    }
}