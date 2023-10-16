package com.pl.exception;

import jakarta.persistence.PersistenceException;

public class UserEmailTakenException extends RuntimeException {
    public UserEmailTakenException(String message) {
        super(message);
    }
}
