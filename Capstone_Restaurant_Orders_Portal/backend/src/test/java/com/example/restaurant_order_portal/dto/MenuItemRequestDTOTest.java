package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for MenuItemRequestDTO.
 */
public class MenuItemRequestDTOTest {

    /**
     * Tests setter and getter methods
     */
    @Test
    void shouldSetAndGetValues() {

        MenuItemRequestDTO menuItemRequestDTO = new MenuItemRequestDTO();

        menuItemRequestDTO.setName("Pizza");
        menuItemRequestDTO.setPrice(200.0);
        menuItemRequestDTO.setCategoryId(10L);
        menuItemRequestDTO.setRestaurantId(5L);
        menuItemRequestDTO.setImageUrl("pizza.png");

        assertEquals("Pizza", menuItemRequestDTO.getName());
        assertEquals(200.0, menuItemRequestDTO.getPrice());
        assertEquals(10L, menuItemRequestDTO.getCategoryId());
        assertEquals(5L, menuItemRequestDTO.getRestaurantId());
        assertEquals("pizza.png", menuItemRequestDTO.getImageUrl());
    }

    /**
     * Tests default object creation
     */
    @Test
    void shouldCreateEmptyObject() {

        MenuItemRequestDTO menuItemRequestDTO = new MenuItemRequestDTO();

        assertNotNull(menuItemRequestDTO);
        assertNull(menuItemRequestDTO.getName());
        assertNull(menuItemRequestDTO.getPrice());
        assertNull(menuItemRequestDTO.getCategoryId());
        assertNull(menuItemRequestDTO.getRestaurantId());
        assertNull(menuItemRequestDTO.getImageUrl());
    }
}