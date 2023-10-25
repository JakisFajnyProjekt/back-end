package com.pl.auth.authentication;


import jakarta.validation.constraints.Pattern;

import java.util.Objects;

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

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterRequest that = (RegisterRequest) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, password);
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

