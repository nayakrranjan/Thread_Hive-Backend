package com.threadhive.exceptions;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
        private Map<String, String> errors;
        private int statusCode;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFoundException(UserNotFoundException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
            false,
            ex.getMessage(),
            null,
            null,
            HttpStatus.NOT_FOUND.value()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(AuthenticationException ex) {
        
        ApiResponse<Object> response = new ApiResponse<>(
            false,
            ex.getMessage(),
            null,
            null,
            HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateUserException(DuplicateUserException ex) {

        ApiResponse<Object> response = new ApiResponse<>(
            false,
            ex.getMessage(),
            null,
            null,
            HttpStatus.CONFLICT.value()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
