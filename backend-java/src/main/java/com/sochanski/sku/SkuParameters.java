package com.sochanski.sku;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
public class SkuParameters {
    @NotEmpty
    public String name;
    public String description;
}
