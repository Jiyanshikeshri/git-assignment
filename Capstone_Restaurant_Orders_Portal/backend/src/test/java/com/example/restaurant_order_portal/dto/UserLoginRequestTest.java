package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for UserLoginRequest DTO.
 */
class UserLoginRequestTest {

    /**
     * Tests setting and getting email and password
     */
    @Test
    void shouldSetAndGetValues() {

        UserLoginRequest userLoginRequest = new UserLoginRequest();

        userLoginRequest.setEmail("test@example.com");
        userLoginRequest.setPassword("password123");

        assertEquals("test@example.com", userLoginRequest.getEmail());
        assertEquals("password123", userLoginRequest.getPassword());
    }

    /**
     * Tests default constructor creates empty object
     */
    @Test
    void shouldCreateEmptyObject() {

        UserLoginRequest userLoginRequest = new UserLoginRequest();

        assertNull(userLoginRequest.getEmail());
        assertNull(userLoginRequest.getPassword());
    }
}