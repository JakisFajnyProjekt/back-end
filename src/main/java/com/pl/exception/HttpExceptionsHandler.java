package com.pl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class HttpExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleFoundException(NotFoundException notFoundException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(notFoundException.getMessage(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDate.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorResponse);
    }

    @ExceptionHandler(UserEmailTakenException.class)
    public ResponseEntity<ApiErrorResponse> handleUserEmailTakenException(UserEmailTakenException userEmailTakenException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(userEmailTakenException.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDate.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
    }

    @ExceptionHandler(InvalidValuesException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidValueException(InvalidValuesException invalidValuesException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(invalidValuesException.getMessage(),
                HttpStatus.BAD_REQUEST.toString(), LocalDate.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
    }


}
