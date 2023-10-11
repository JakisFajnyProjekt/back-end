package com.pl.model.dto;

public record UserDTO(
        Long id,
        String name,
        String password,
        String email
) {
}

