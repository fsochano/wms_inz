package com.sochanski.location;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Validated
public class LocationParameters {
    @NotEmpty
    public String name;
    @NotNull
    public LocationType locationType;
    @Positive
    public long capacity;
}
