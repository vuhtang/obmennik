package org.highload.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class WeHaveNoManeyException extends RuntimeException{
    private final String message;
}
