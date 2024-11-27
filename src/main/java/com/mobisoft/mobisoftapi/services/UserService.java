package com.mobisoft.mobisoftapi.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mobisoft.mobisoftapi.models.User;

@Service
public class UserService {

	public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Usuário não autenticado");
        }

        User user = (User) authentication.getPrincipal();
        return user;
    }
}
