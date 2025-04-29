package com.threadhive.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
@Getter
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException {
    private final Map<String, String> errors;
    
    public InvalidInputException(String message) {
        super(message);
        this.errors = new HashMap<>();
    }

    public InvalidInputException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

}
