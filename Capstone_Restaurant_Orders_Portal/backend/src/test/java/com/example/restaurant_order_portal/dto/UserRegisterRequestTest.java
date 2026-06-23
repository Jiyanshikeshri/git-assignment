package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for UserRegisterRequest DTO.
 */
class UserRegisterRequestTest {

    /**
     * Tests setting and getting all fields
     */
    @Test
    void shouldSetAndGetAllValues() {

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();

        userRegisterRequest.setFirstName("Jiyanshi");
        userRegisterRequest.setLastName("Keshri");
        userRegisterRequest.setEmail("jiyanshi@example.com");
        userRegisterRequest.setPassword("secure123");
        userRegisterRequest.setPhoneNumber("9876543210");
        userRegisterRequest.setRole("USER");

        assertEquals("Jiyanshi", userRegisterRequest.getFirstName());
        assertEquals("Keshri", userRegisterRequest.getLastName());
        assertEquals("jiyanshi@example.com", userRegisterRequest.getEmail());
        assertEquals("secure123", userRegisterRequest.getPassword());
        assertEquals("9876543210", userRegisterRequest.getPhoneNumber());
        assertEquals("USER", userRegisterRequest.getRole());
    }

    /**
     * Tests default constructor creates empty object
     */
    @Test
    void shouldCreateEmptyObject() {

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();

        assertNull(userRegisterRequest.getFirstName());
        assertNull(userRegisterRequest.getLastName());
        assertNull(userRegisterRequest.getEmail());
        assertNull(userRegisterRequest.getPassword());
        assertNull(userRegisterRequest.getPhoneNumber());
        assertNull(userRegisterRequest.getRole());
    }
}