package com.weylandyutani.synthetichumancorestarter.exception;

import com.weylandyutani.synthetichumancorestarter.model.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(new ErrorResponse("VALIDATION_ERROR", ex.getMessage(), LocalDateTime.now()) {}, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QueueFullException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ErrorResponse> handleQueueFullException(QueueFullException ex) {
        return new ResponseEntity<>(new ErrorResponse("QUEUE_FULL", ex.getMessage(), LocalDateTime.now()), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
