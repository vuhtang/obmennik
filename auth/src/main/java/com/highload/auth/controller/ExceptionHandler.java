package com.highload.auth.controller;

import com.highload.auth.exeptions.UserAlreadyPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> handleResourceNotFound(UserAlreadyPresentException exception) {
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.NOT_ACCEPTABLE
        );
    }
}
