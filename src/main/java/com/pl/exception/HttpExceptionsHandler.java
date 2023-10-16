package com.pl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class HttpExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoudException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(NotFoudException notFoudException){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(notFoudException.getMessage(),
                HttpStatus.FORBIDDEN.toString(),
                LocalDate.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiErrorResponse);
    }

    @ExceptionHandler(UserEmailTakenException.class)
    public ResponseEntity<ApiErrorResponse> handleUserEmailTakenException(UserEmailTakenException userEmailTakenException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(userEmailTakenException.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDate.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
    }



}
