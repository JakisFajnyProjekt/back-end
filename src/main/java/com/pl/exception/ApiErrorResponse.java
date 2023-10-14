package com.pl.exception;

import java.time.LocalDate;

record ApiErrorResponse(String message, String code, LocalDate localDate) {
}
