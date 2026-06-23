package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for CategoryResponseDTO.
 */
public class CategoryResponseDTOTest {

    /**
     * Tests constructor and getter methods
     */
    @Test
    void shouldCreateObjectUsingConstructor() {

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(
                1L,
                "Drinks",
                10L,
                "Dominos",
                "image.png"
        );

        assertEquals(1L, categoryResponseDTO.getId());
        assertEquals("Drinks", categoryResponseDTO.getName());
        assertEquals(10L, categoryResponseDTO.getRestaurantId());
        assertEquals("Dominos", categoryResponseDTO.getRestaurantName());
        assertEquals("image.png", categoryResponseDTO.getImageUrl());
    }

    /**
     * Ensures object is not null after creation
     */
    @Test
    void shouldNotBeNull() {

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(
                2L,
                "Desserts",
                20L,
                "KFC",
                "dessert.png"
        );

        assertNotNull(categoryResponseDTO);
    }
}