package com.sochanski.pick.data;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public class PickTaskStatusParam {
    @NotNull
    public PickTaskStatus status;
}
