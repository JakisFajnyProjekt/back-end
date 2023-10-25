package com.pl.auth.authentication;

import java.util.Objects;

public class LoginRequest extends AbstractAuthRequest {

    public LoginRequest() {}

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
    private LoginRequest(Builder builder){
        setEmail(builder.email);
        setPassword(builder.password);
    }


    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private String email;
        private String password;

        public Builder email(String email){
            this.email=email;
            return this;
        }
        public Builder password(String password){
            this.password = password;
            return this;
        }
        public LoginRequest build(){
            return new LoginRequest(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginRequest that = (LoginRequest) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
