package com.sochanski.pick;

import com.sochanski.pick.data.PickTaskStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CannotChangePickTaskStatusException extends ResponseStatusException {
    public CannotChangePickTaskStatusException(PickTaskStatus from, PickTaskStatus to) {
        super(HttpStatus.FORBIDDEN, "Cannot change pick task status from " + from + " to " + to);
    }
}