package com.sochanski.pick;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public class PickTaskStatusParam {
    @NotNull
    public PickTaskStatus status;
}
