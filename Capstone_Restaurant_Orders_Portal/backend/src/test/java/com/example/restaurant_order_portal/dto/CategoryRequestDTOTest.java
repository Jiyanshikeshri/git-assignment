package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for CategoryRequestDTO.
 */
class CategoryRequestDTOTest {

    /**
     * Tests setter and getter methods
     */
    @Test
    void shouldSetAndGetValues() {

        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();

        categoryRequestDTO.setName("Drinks");
        categoryRequestDTO.setRestaurantId(10L);
        categoryRequestDTO.setImageUrl("image.png");

        assertEquals("Drinks", categoryRequestDTO.getName());
        assertEquals(10L, categoryRequestDTO.getRestaurantId());
        assertEquals("image.png", categoryRequestDTO.getImageUrl());
    }

    /**
     * Tests default constructor
     */
    @Test
    void shouldCreateEmptyObject() {

        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();

        assertNotNull(categoryRequestDTO);
        assertNull(categoryRequestDTO.getName());
        assertNull(categoryRequestDTO.getRestaurantId());
        assertNull(categoryRequestDTO.getImageUrl());
    }
}