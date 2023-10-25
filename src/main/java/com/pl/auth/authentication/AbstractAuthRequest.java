package com.pl.auth.authentication;

import jakarta.validation.constraints.*;

public abstract class AbstractAuthRequest {
    @Pattern(
            regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$",
            message = "{validation.email.pattern.message}"
    )
    protected String email;
    @NotBlank(message = "{validation.field.blank}")
    @Pattern(regexp = "^.{6,}$", message = "{validation.password.min}")
    @Pattern(regexp = "^.{0,29}$", message = "{validation.password.max}")
    @Pattern(regexp = ".*\\d.*", message = "{validation.password.digits}")
    @Pattern(regexp = "^(?=.*[a-z]).*$", message = "{validation.password.lowerCase}")
    @Pattern(regexp = "^(?=.*[A-Z]).*$", message = "{validation.password.capital}")
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