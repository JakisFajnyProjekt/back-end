package com.pl.auth.authentication;

public class LoginRequest extends AbstractAuthRequest {
    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private LoginRequest(Builder builder) {
        setEmail(builder.email);
        setPassword(builder.password);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String email;
        private String password;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public LoginRequest build() {
            return new LoginRequest(this);
        }
    }
}
