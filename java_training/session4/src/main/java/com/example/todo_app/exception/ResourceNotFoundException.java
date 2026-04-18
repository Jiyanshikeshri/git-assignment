package com.example.todo_app.exception;

// Custom exception for not found cases
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}