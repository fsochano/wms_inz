package com.sochanski.container;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotEnoughSpaceInLocationException extends ResponseStatusException {
    public NotEnoughSpaceInLocationException() {
        super(HttpStatus.FORBIDDEN, "Not enough space in Location");
    }
}
