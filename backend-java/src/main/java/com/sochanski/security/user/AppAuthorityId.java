package com.sochanski.security.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppAuthorityId implements Serializable {

    private String username;
    private String authority;

}
