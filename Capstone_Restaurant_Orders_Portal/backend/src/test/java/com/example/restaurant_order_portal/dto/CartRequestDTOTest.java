package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for CartRequestDTO.
 */
class CartRequestDTOTest {

    /**
     * Test constructor
     */
    @Test
    void shouldCreateObjectUsingConstructor() {

        CartRequestDTO cartRequestDTO = new CartRequestDTO(1L, 10L);

        assertEquals(1L, cartRequestDTO.getUserId());
        assertEquals(10L, cartRequestDTO.getRestaurantId());
    }

    /**
     * Test setters and getters
     */
    @Test
    void shouldSetAndGetFieldsCorrectly() {

        CartRequestDTO cartRequestDTO = new CartRequestDTO();

        cartRequestDTO.setUserId(5L);
        cartRequestDTO.setRestaurantId(20L);

        assertEquals(5L, cartRequestDTO.getUserId());
        assertEquals(20L, cartRequestDTO.getRestaurantId());
    }

    /**
     * Test default constructor values
     */
    @Test
    void shouldHaveNullValuesInitially() {

        CartRequestDTO cartRequestDTO = new CartRequestDTO();

        assertNull(cartRequestDTO.getUserId());
        assertNull(cartRequestDTO.getRestaurantId());
    }

    /**
     * Test object state after updates
     */
    @Test
    void shouldMaintainCorrectStateAfterUpdates() {

        CartRequestDTO cartRequestDTO = new CartRequestDTO();

        cartRequestDTO.setUserId(1L);
        cartRequestDTO.setRestaurantId(2L);

        cartRequestDTO.setUserId(99L);
        cartRequestDTO.setRestaurantId(88L);

        assertEquals(99L, cartRequestDTO.getUserId());
        assertEquals(88L, cartRequestDTO.getRestaurantId());
    }
}