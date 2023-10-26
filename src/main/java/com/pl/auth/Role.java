package com.pl.auth;

public enum Role {
    USER,
    ADMIN
    ;


    public String getName() {
        return this.toString().toLowerCase();
    }
}
