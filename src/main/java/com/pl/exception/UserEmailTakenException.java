package com.pl.exception;

public class UserEmailTakenException extends RuntimeException {
    public UserEmailTakenException(String message) {
        super(message);
    }
}
