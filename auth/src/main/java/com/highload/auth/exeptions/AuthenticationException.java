package com.highload.auth.exeptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationException extends Exception {
    private final String message;
}
