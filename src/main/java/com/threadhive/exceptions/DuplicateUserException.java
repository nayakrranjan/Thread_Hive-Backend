package com.threadhive.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateUserException extends RuntimeException {
    private final Map<String, String> errors;
    
    public DuplicateUserException(String message) {
        super(message);
        this.errors = new HashMap<>();
    }

    public DuplicateUserException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

}

