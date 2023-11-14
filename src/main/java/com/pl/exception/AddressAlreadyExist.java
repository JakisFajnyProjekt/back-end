package com.pl.exception;

import jakarta.persistence.NonUniqueResultException;

public class AddressAlreadyExist extends RuntimeException {
    public AddressAlreadyExist(String message) {
        super(message);
    }
}
