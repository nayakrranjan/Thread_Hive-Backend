package com.threadhive.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private final Map<String, String> errors;
    
    public UserNotFoundException(String message) {
        super(message);
        this.errors = new HashMap<>();
    }
    
    public UserNotFoundException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

}