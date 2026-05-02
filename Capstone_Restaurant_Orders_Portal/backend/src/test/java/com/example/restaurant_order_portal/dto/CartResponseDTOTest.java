package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for CartResponseDTO.
 */
class CartResponseDTOTest {

    /**
     * Tests parameterized constructor and getters
     */
    @Test
    void shouldCreateObjectUsingConstructor() {

        CartResponseDTO cartResponseDTO = new CartResponseDTO(1L, 10L, 100L);

        assertEquals(1L, cartResponseDTO.getCartId());
        assertEquals(10L, cartResponseDTO.getUserId());
        assertEquals(100L, cartResponseDTO.getRestaurantId());
    }

    /**
     * Tests setter and getter methods
     */
    @Test
    void shouldSetAndGetValues() {

        CartResponseDTO cartResponseDTO = new CartResponseDTO();

        cartResponseDTO.setCartId(2L);
        cartResponseDTO.setUserId(20L);
        cartResponseDTO.setRestaurantId(200L);

        assertEquals(2L, cartResponseDTO.getCartId());
        assertEquals(20L, cartResponseDTO.getUserId());
        assertEquals(200L, cartResponseDTO.getRestaurantId());
    }

    /**
     * Tests default constructor
     */
    @Test
    void shouldCreateEmptyObject() {

        CartResponseDTO cartResponseDTO = new CartResponseDTO();

        assertNotNull(cartResponseDTO);
    }
}