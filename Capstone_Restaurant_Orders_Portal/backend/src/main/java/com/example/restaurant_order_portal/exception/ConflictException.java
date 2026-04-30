package com.example.restaurant_order_portal.exception;

/**
 * Exception thrown when a request conflicts with current state.
 */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
