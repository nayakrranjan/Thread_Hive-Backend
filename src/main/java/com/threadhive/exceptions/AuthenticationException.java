package com.threadhive.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends RuntimeException {
    private final Map<String, String> errors;

    public AuthenticationException(String message) {
        super(message);
        this.errors = new HashMap<>();
    }

    public AuthenticationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

}