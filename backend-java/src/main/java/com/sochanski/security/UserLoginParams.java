package com.sochanski.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Validated
public class UserLoginParams {
    @NotEmpty(message = "Must contain username")
    private String username;
    @NotEmpty(message = "Must contain password")
    private String password;
}