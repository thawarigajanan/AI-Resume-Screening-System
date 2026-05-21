package com.example.resumescreening.exception;

public class ResourceNotFoundException
        extends RuntimeException {

    public ResourceNotFoundException(
            String message) {

        super(message);
    }
}