package com.pl.auth.authentication;


import jakarta.validation.constraints.Pattern;


public class RegisterRequest extends AbstractAuthRequest {
    @Pattern(regexp = "^.{2,}$", message = "{validation.name.min}")
    @Pattern(regexp = "^.{0,50}$", message = "{validation.name.max}")
    @Pattern(regexp = "^\\D*$", message = "{validation.name.digitsNotAllowed}")
    private String firstName;
    @Pattern(regexp = "^.{2,}$", message = "{validation.name.min}")
    @Pattern(regexp = "^.{0,50}$", message = "{validation.name.max}")
    @Pattern(regexp = "^\\D*$", message = "{validation.name.digitsNotAllowed}")
    private String lastName;

    public RegisterRequest() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static class Builder {
        private String firstName;
        private String lastName;

        private String email;
        private String password;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public RegisterRequest build() {
            RegisterRequest request = new RegisterRequest();
            request.setFirstName(firstName);
            request.setLastName(lastName);
            request.setEmail(email);
            request.setPassword(password);
            return request;
        }
    }
}

