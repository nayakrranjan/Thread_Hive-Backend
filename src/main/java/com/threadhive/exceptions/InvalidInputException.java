package com.threadhive.exceptions;

import java.util.HashMap;
import java.util.Map;

public class InvalidInputException extends RuntimeException {
    private Map<String, String> errors;
    
    public InvalidInputException(String message) {
        super(message);
        this.errors = new HashMap<>();
    }

    public InvalidInputException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return this.errors;
    }
}
