package com.pl.security;

public enum Role {
    USER,
    ADMIN
    ;


    public String getName() {
        return this.toString().toLowerCase();
    }
}
