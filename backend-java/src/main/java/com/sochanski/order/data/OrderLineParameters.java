package com.sochanski.order.data;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Validated
public class OrderLineParameters {
    @Positive
    public long qty;

    @NotNull
    public long skuId;
}
