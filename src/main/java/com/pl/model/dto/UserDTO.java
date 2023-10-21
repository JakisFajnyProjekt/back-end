package com.pl.model.dto;

import com.pl.security.Role;

public record UserDTO(

        String firstName,


        String lastName,

        String email,

        String password,
        Role role
) {
}

