package org.highload.controller;

import org.highload.exceptions.WeHaveNoManeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WeHaveNoManeyException.class)
    public ResponseEntity<?> handleResourceNotFound(WeHaveNoManeyException exception) {
        return new ResponseEntity<>
                (
                        exception.getMessage(),
                        HttpStatus.NOT_ACCEPTABLE
                );
    }
}
