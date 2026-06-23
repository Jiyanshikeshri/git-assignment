package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for CartItemResponseDTO
 */
class CartItemResponseDTOTest {

    /**
     * Test all-args constructor
     */
    @Test
    void shouldCreateObjectUsingConstructor() {

        CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO(
                1L,
                2L,
                3L,
                4,
                "Pizza",
                199.99
        );

        assertEquals(1L, cartItemResponseDTO.getCartItemId());
        assertEquals(2L, cartItemResponseDTO.getCartId());
        assertEquals(3L, cartItemResponseDTO.getMenuItemId());
        assertEquals(4, cartItemResponseDTO.getQuantity());
        assertEquals("Pizza", cartItemResponseDTO.getMenuItemName());
        assertEquals(199.99, cartItemResponseDTO.getPrice());
    }

    /**
     * Test setters and getters
     */
    @Test
    void shouldSetAndGetFieldsCorrectly() {

        CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO();

        cartItemResponseDTO.setCartItemId(10L);
        cartItemResponseDTO.setCartId(20L);
        cartItemResponseDTO.setMenuItemId(30L);
        cartItemResponseDTO.setQuantity(2);
        cartItemResponseDTO.setMenuItemName("Burger");
        cartItemResponseDTO.setPrice(149.50);

        assertEquals(10L, cartItemResponseDTO.getCartItemId());
        assertEquals(20L, cartItemResponseDTO.getCartId());
        assertEquals(30L, cartItemResponseDTO.getMenuItemId());
        assertEquals(2, cartItemResponseDTO.getQuantity());
        assertEquals("Burger", cartItemResponseDTO.getMenuItemName());
        assertEquals(149.50, cartItemResponseDTO.getPrice());
    }

    /**
     * Test default constructor values
     */
    @Test
    void shouldHaveNullValuesInitially() {

        CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO();

        assertNull(cartItemResponseDTO.getCartItemId());
        assertNull(cartItemResponseDTO.getCartId());
        assertNull(cartItemResponseDTO.getMenuItemId());
        assertNull(cartItemResponseDTO.getQuantity());
        assertNull(cartItemResponseDTO.getMenuItemName());
        assertNull(cartItemResponseDTO.getPrice());
    }

    /**
     * Test object state after updates
     */
    @Test
    void shouldMaintainCorrectStateAfterUpdates() {

        CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO();

        cartItemResponseDTO.setCartItemId(1L);
        cartItemResponseDTO.setCartId(2L);
        cartItemResponseDTO.setMenuItemId(3L);
        cartItemResponseDTO.setQuantity(1);
        cartItemResponseDTO.setMenuItemName("Pizza");
        cartItemResponseDTO.setPrice(100.0);

        cartItemResponseDTO.setCartItemId(99L);
        cartItemResponseDTO.setCartId(88L);
        cartItemResponseDTO.setMenuItemId(77L);
        cartItemResponseDTO.setQuantity(5);
        cartItemResponseDTO.setMenuItemName("Pasta");
        cartItemResponseDTO.setPrice(250.0);

        assertEquals(99L, cartItemResponseDTO.getCartItemId());
        assertEquals(88L, cartItemResponseDTO.getCartId());
        assertEquals(77L, cartItemResponseDTO.getMenuItemId());
        assertEquals(5, cartItemResponseDTO.getQuantity());
        assertEquals("Pasta", cartItemResponseDTO.getMenuItemName());
        assertEquals(250.0, cartItemResponseDTO.getPrice());
    }
}