package org.highload.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    private final String message;
}
