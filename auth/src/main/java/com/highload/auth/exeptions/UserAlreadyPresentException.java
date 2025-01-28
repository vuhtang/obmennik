package com.highload.auth.exeptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAlreadyPresentException extends Exception {
    private final String message;
}
