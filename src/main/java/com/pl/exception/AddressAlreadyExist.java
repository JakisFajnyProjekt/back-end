package com.pl.exception;

public class AddressAlreadyExist extends RuntimeException {
    public AddressAlreadyExist(String message) {
        super(message);
    }
}
