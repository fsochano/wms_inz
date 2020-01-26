package com.sochanski.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private String token;
    private List<String> authorities;
}
