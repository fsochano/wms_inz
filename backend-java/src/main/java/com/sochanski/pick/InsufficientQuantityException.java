package com.sochanski.pick;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InsufficientQuantityException extends ResponseStatusException {
    public InsufficientQuantityException() {
        super(HttpStatus.FORBIDDEN, "Not enough quantity");
    }
}
