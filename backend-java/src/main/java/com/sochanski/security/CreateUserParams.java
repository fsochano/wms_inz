package com.sochanski.security;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserParams {
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    private String password;
}
