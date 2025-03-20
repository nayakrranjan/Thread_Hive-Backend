package com.threadhive.exceptions;

import java.util.HashMap;
import java.util.Map;

public class DuplicateUserException extends RuntimeException {
    private Map<String, String> errors;
    
    public DuplicateUserException(String message) {
        super(message);
        this.errors = new HashMap<>();
    }

    public DuplicateUserException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return this.errors;
    }
}

