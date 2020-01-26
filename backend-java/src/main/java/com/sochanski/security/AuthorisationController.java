package com.sochanski.security;

import com.sochanski.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = ApiUtils.BASE_API_PATH)
@CrossOrigin
public class AuthorisationController {

    private AuthenticationManager service;

    @Autowired
    AuthorisationController(AuthenticationManager service){
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginParams userLoginParams) {
        try {
            Authentication a = service.authenticate(new UsernamePasswordAuthenticationToken(userLoginParams.getUsername(), userLoginParams.getPassword()));
            if(a.isAuthenticated()) {
                String preToken = userLoginParams.getUsername()+":"+ userLoginParams.getPassword();
                String token = "Basic " + Base64.getEncoder().encodeToString(preToken.getBytes());
                List<String> roles = a.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(new UserDTO(1L, userLoginParams.getUsername(), token, roles));
            }
            return ResponseEntity.status(401).build();
        } catch(AuthenticationException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}