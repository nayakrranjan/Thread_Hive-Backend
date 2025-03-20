package com.threadhive.exceptions;

import java.util.HashMap;
import java.util.Map;

public class UserNotFoundException extends RuntimeException {
    private Map<String, String> errors;
    
    public UserNotFoundException(String message) {
        super(message);
        this.errors = new HashMap<>();
    }
    
    public UserNotFoundException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
    
    public Map<String, String> getErrors() {
        return this.errors;
    }
}