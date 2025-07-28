package com.weylandyutani.synthetichumancorestarter.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationException(ConstraintViolationException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, "VALIDATION_ERROR")
                .detail(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QueueFullException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ErrorResponse> handleQueueFullException(QueueFullException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.SERVICE_UNAVAILABLE, "QUEUE_FULL")
                .detail(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
