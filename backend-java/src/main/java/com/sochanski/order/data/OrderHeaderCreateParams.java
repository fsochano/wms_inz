package com.sochanski.order.data;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
public class OrderHeaderCreateParams {
    @NotEmpty
    public String name;
    public String last_changed_by;
}
