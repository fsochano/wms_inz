package com.sochanski.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.Callable;

import static com.sochanski.security.SecurityConfiguration.SYSTEM;

public class SecurityUtils {

    public static void setSystemUser() {
        var emptyContext = SecurityContextHolder.createEmptyContext();
        var token = new UsernamePasswordAuthenticationToken(SYSTEM, SYSTEM.getPassword(), SYSTEM.getAuthorities());
        emptyContext.setAuthentication(token);
        SecurityContextHolder.setContext(emptyContext);
    }

    public static <T> T executeAsSystem(Callable<T> toExecute) throws Exception {
        var systemContext = SecurityContextHolder.createEmptyContext();
        var token = new UsernamePasswordAuthenticationToken(SYSTEM, SYSTEM.getPassword(), SYSTEM.getAuthorities());
        systemContext.setAuthentication(token);
        var previousContext = SecurityContextHolder.getContext();
        SecurityContextHolder.setContext(systemContext);
        try{
            return toExecute.call();
        } finally {
            SecurityContextHolder.setContext(previousContext);
        }
    }
}
