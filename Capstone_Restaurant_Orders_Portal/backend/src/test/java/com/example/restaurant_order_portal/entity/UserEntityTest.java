package com.example.restaurant_order_portal.entity;

import com.example.restaurant_order_portal.enums.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for User entity.
 */
class UserTest {

    /**
     * Test getters and setters
     */
    @Test
    void shouldSetAndGetFields() {

        User user = new User();

        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setPassword("1234");
        user.setPhoneNumber("9999999999");
        user.setRole(Role.USER);
        user.setWalletBalance(500.0);

        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("1234", user.getPassword());
        assertEquals("9999999999", user.getPhoneNumber());
        assertEquals(Role.USER, user.getRole());
        assertEquals(500.0, user.getWalletBalance());
    }

    /**
     * Test constructor
     */
    @Test
    void shouldCreateUserUsingConstructor() {

        User user = new User(
                "John",
                "Doe",
                "john@example.com",
                "1234",
                "9999999999",
                Role.USER
        );

        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
    }

    /**
     * Test equals and hashCode
     */
    @Test
    void shouldBeEqualWhenIdsMatch() {

        User user1 = new User();
        User user2 = new User();

        user1.setId(1L);
        user2.setId(1L);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    /**
     * Test PrePersist
     */
    @Test
    void shouldSetCreatedAtOnPersist() {

        User user = new User();

        user.setCreatedAt();

        assertNotNull(user.getCreatedAt());
        assertTrue(user.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    /**
     * Test toString
     */
    @Test
    void shouldReturnString() {

        User user = new User();
        user.setId(1L);

        String result = user.toString();

        assertTrue(result.contains("1"));
    }
}