package com.sochanski.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin
public class AuthorisationController {

    private AuthenticationManager service;

    @Autowired
    AuthorisationController(AuthenticationManager service){
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        try {
            Authentication a = service.authenticate(new UsernamePasswordAuthenticationToken(user.username, user.password));
            if(a.isAuthenticated()) {
                String preToken = user.username+":"+user.password;
                String token = "Basic " + Base64.getEncoder().encodeToString(preToken.getBytes());
                return ResponseEntity.ok(new UserDTO(1L, user.username, token));
            }
            return ResponseEntity.status(401).build();
        } catch(AuthenticationException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}