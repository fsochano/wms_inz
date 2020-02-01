package com.sochanski.security;

import lombok.Data;

import java.util.List;

@Data
public class LoggedInUser extends UserDTO {
    private String token;

    public LoggedInUser(String username, String token, List<String> authorities) {
        super(username, authorities);
        this.token = token;
    }
}
