package com.pl.model.dto;

public record UserUpdateDTO(String firstName,
                            String lastName,
                            String email,
                            String password
) {}
