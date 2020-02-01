package com.sochanski.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private List<String> authorities;

    public UserDTO(UserDetails details) {
        username = details.getUsername();
        authorities = details.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(toList());
    }
}
