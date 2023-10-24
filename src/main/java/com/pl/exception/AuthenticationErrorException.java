package com.pl.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;


public class AuthenticationErrorException extends Throwable {
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