package com.sochanski.container;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Validated
public class ContainerParameters {
    @NotNull
    public long locationId;

    @PositiveOrZero
    public int containerSize;

    @NotNull
    public long skuId;

    @PositiveOrZero
    public int skuQty;

    @Positive
    public int skuCapacity;
}
