package com.sochanski.order.data;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
public class OrderCreateParams {
    @NotEmpty
    public String name;
}
