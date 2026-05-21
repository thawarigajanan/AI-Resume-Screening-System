package com.example.resumescreening.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Custom Exception
    @ExceptionHandler(
            ResourceNotFoundException.class)

    public ResponseEntity<?> handleResourceNotFound(

            ResourceNotFoundException ex) {

        Map<String, Object> error =
                new HashMap<>();

        error.put("message",
                ex.getMessage());

        error.put("status", 404);

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND);
    }

    // Validation Exception
    @ExceptionHandler(
            MethodArgumentNotValidException.class)

    public ResponseEntity<?> handleValidation(

            MethodArgumentNotValidException ex) {

        Map<String, String> errors =
                new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->

                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()));

        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST);
    }

    // General Exception
    @ExceptionHandler(Exception.class)

    public ResponseEntity<?> handleException(
            Exception ex) {

        Map<String, Object> error =
                new HashMap<>();

        error.put("message",
                ex.getMessage());

        error.put("status", 500);

        return new ResponseEntity<>(
                error,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}