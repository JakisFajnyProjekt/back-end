package com.pl.exception;

import jdk.jshell.Snippet;
import org.hibernate.engine.spi.Status;

import java.util.List;
import java.util.Map;

public class ValidationErrorResponse {
    private String message;
    private Map<String, List<String>> errors;

    public String getMessage() {
        return message;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
