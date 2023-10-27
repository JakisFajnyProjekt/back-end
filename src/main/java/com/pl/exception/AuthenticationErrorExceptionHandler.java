package com.pl.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@PropertySource("classpath:messages.properties")
public class AuthenticationErrorExceptionHandler {

    @Value("${authentication.invalidCredentials}")
    private String invalidCredentials;

    @ExceptionHandler(AuthenticationErrorException.class)
    public ResponseEntity<ValidationErrorResponse> handleAuthenticationErrorException(AuthenticationErrorException ex) {
        ValidationErrorResponse response = new ValidationErrorResponse() {
        };
        response.setMessage(invalidCredentials);
        AuthenticationError cause = ex.getType();
        if (Objects.equals(cause, AuthenticationError.EMAIL)) {
            response.setErrors(Map.of(ex.getType().toString(), Collections.singletonList(ex.getMessage()) ));
        } else if (Objects.equals(cause, AuthenticationError.PASSWORD)) {
            response.setErrors(Map.of(ex.getType().toString(), Collections.singletonList(ex.getMessage()) ));
        } else {
            response.setErrors(Map.of("credentials", Collections.singletonList("unknown error") ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
