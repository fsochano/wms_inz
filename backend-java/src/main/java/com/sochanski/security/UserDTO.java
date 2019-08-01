package com.sochanski.security;

public class UserDTO {
    public long id;
    public String name;
    public String token;

    public UserDTO(long id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }
}
