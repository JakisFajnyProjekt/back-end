package com.pl.exception;

public class AuthenticationErrorException extends RuntimeException {

    private final AuthenticationError type;
    private final String message;

    public AuthenticationErrorException(AuthenticationError type, String message) {
        this.type = type;
        this.message = message;
    }

    public AuthenticationError getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }
}