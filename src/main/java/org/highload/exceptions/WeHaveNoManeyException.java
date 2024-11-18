package org.highload.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeHaveNoManeyException extends RuntimeException{
    private final String message;
}
