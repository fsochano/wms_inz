package com.sochanski.security;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
public class UserLoginParams {
    @NotEmpty(message = "Must contain username")
    public String username;
    @NotEmpty(message = "Must contain password")
    public String password;
}