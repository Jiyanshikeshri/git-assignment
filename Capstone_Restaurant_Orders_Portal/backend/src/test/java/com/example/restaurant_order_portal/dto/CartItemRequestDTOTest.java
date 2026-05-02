package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for CartItemRequestDTO
 */
class CartItemRequestDTOTest {

    /**
     * Test constructor
     */
    @Test
    void shouldCreateObjectUsingConstructor() {

        CartItemRequestDTO cartItemRequestDTO =
                new CartItemRequestDTO(1L, 10L, 2);

        assertEquals(1L, cartItemRequestDTO.getUserId());
        assertEquals(10L, cartItemRequestDTO.getMenuItemId());
        assertEquals(2, cartItemRequestDTO.getQuantity());
    }

    /**
     * Test setters and getters
     */
    @Test
    void shouldSetAndGetFieldsCorrectly() {

        CartItemRequestDTO cartItemRequestDTO = new CartItemRequestDTO();

        cartItemRequestDTO.setUserId(5L);
        cartItemRequestDTO.setMenuItemId(20L);
        cartItemRequestDTO.setQuantity(3);

        assertEquals(5L, cartItemRequestDTO.getUserId());
        assertEquals(20L, cartItemRequestDTO.getMenuItemId());
        assertEquals(3, cartItemRequestDTO.getQuantity());
    }

    /**
     * Test default constructor values
     */
    @Test
    void shouldHaveNullValuesInitially() {

        CartItemRequestDTO cartItemRequestDTO = new CartItemRequestDTO();

        assertNull(cartItemRequestDTO.getUserId());
        assertNull(cartItemRequestDTO.getMenuItemId());
        assertNull(cartItemRequestDTO.getQuantity());
    }

    /**
     * Test data integrity
     */
    @Test
    void shouldMaintainCorrectStateAfterUpdates() {

        CartItemRequestDTO cartItemRequestDTO = new CartItemRequestDTO();

        cartItemRequestDTO.setUserId(1L);
        cartItemRequestDTO.setMenuItemId(2L);
        cartItemRequestDTO.setQuantity(1);

        cartItemRequestDTO.setUserId(99L);
        cartItemRequestDTO.setMenuItemId(100L);
        cartItemRequestDTO.setQuantity(5);

        assertEquals(99L, cartItemRequestDTO.getUserId());
        assertEquals(100L, cartItemRequestDTO.getMenuItemId());
        assertEquals(5, cartItemRequestDTO.getQuantity());
    }
}