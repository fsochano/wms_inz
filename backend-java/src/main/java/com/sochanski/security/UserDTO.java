package com.sochanski.security;

import java.util.List;

public class UserDTO {
    public long id;
    public String name;
    public String token;
    public List<String> authorities;

    public UserDTO(long id, String name, String token, List<String> authorities) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.authorities = authorities;
    }
}
