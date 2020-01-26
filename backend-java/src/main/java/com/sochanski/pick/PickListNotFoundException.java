package com.sochanski.pick;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PickListNotFoundException extends ResponseStatusException {
    public PickListNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Pick list not found");
    }
}
