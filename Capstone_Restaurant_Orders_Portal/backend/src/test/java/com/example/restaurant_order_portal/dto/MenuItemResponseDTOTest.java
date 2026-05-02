package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for MenuItemResponseDTO.
 */
class MenuItemResponseDTOTest {

    /**
     * Tests constructor and getter methods
     */
    @Test
    void shouldCreateObjectUsingConstructor() {

        MenuItemResponseDTO menuItemResponseDTO = new MenuItemResponseDTO(
                1L,
                "Pizza",
                200.0,
                "Fast Food",
                "Dominos",
                "pizza.png"
        );

        assertEquals(1L, menuItemResponseDTO.getId());
        assertEquals("Pizza", menuItemResponseDTO.getName());
        assertEquals(200.0, menuItemResponseDTO.getPrice());
        assertEquals("Fast Food", menuItemResponseDTO.getCategoryName());
        assertEquals("Dominos", menuItemResponseDTO.getRestaurantName());
        assertEquals("pizza.png", menuItemResponseDTO.getImageUrl());
    }

    /**
     * Ensures object is not null after creation
     */
    @Test
    void shouldNotBeNull() {

        MenuItemResponseDTO menuItemResponseDTO = new MenuItemResponseDTO(
                2L,
                "Burger",
                150.0,
                "Snacks",
                "KFC",
                "burger.png"
        );

        assertNotNull(menuItemResponseDTO);
    }
}