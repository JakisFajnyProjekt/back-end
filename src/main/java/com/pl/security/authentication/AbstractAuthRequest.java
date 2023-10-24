package com.pl.security.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public abstract class AbstractAuthRequest {
    @Email(message = "Email is required")
    protected String email;
    @NotNull(message = "Password is required")
    @NotBlank(message = "The field cannot be blank or contain only whitespace.")
    protected String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}