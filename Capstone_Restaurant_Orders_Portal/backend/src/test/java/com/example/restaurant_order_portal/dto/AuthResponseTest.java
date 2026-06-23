package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for AuthResponse DTO
 */
class AuthResponseTest {

    /**
     * Test constructor
     */
    @Test
    void shouldCreateObjectUsingConstructor() {

        AuthResponse response =
                new AuthResponse("jwt-token", "user@example.com", "USER", 1L);

        assertEquals("jwt-token", response.getToken());
        assertEquals("user@example.com", response.getEmail());
        assertEquals("USER", response.getRole());
        assertEquals(1L, response.getUserId());
    }

    /**
     * Test default constructor values
     */
    @Test
    void shouldHaveNullValuesWithDefaultConstructor() {

        AuthResponse response = new AuthResponse();

        assertNull(response.getToken());
        assertNull(response.getEmail());
        assertNull(response.getRole());
        assertNull(response.getUserId());
    }

    /**
     * Test getter
     */
    @Test
    void shouldReturnCorrectValuesFromGetters() {

        AuthResponse response =
                new AuthResponse("abc123", "test@mail.com", "ADMIN", 99L);

        assertEquals("abc123", response.getToken());
        assertEquals("test@mail.com", response.getEmail());
        assertEquals("ADMIN", response.getRole());
        assertEquals(99L, response.getUserId());
    }
}