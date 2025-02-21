package org.example.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SessionUser {
    public CustomUserDetails get(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails sessionUser) {
            return sessionUser;
        }

        return null;
    }
}
