package com.sochanski.pick;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PickTaskNotFoundException extends ResponseStatusException {
    public PickTaskNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Pick task not found");
    }
}
