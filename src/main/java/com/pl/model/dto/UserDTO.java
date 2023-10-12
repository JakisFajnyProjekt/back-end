package com.pl.model.dto;

import com.pl.security.Role;

public record UserDTO(
        String name,
        String password,
        String email,
        Role role
) {
}

