package com.pl.exception;

import jakarta.persistence.NonUniqueResultException;

public class UserEmailTakenException extends RuntimeException {
    public UserEmailTakenException(String message) {
        super(message);
    }
}
