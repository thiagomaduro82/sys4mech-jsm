package com.sys4business.sys4mech.exceptions;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e) {
        StandardError error = new StandardError(e.getMessage(), System.currentTimeMillis(),
                HttpStatus.NOT_FOUND.value());
        log.error("Object not found: {} - Status code: {}", error.getMessage(), error.getStatus());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e) {
        ValidationError error = new ValidationError("Validation error", System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value());
        e.getBindingResult().getFieldErrors().forEach(fieldError -> 
            error.addErrors(fieldError.getField(), fieldError.getDefaultMessage()));
        log.error("Validation error: {} - Status code: {}", error.getMessage(), error.getStatus());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
