package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for RestaurantResponseDTO.
 */
public class RestaurantResponseDTOTest {

    /**
     * Tests object creation using parameterized constructor
     */
    @Test
    void shouldCreateObjectUsingConstructor() {

        RestaurantResponseDTO restaurantResponseDTO = new RestaurantResponseDTO(
                1L,
                "Dominos",
                "OPEN",
                "John Doe",
                "image.png"
        );

        assertEquals(1L, restaurantResponseDTO.getId());
        assertEquals("Dominos", restaurantResponseDTO.getName());
        assertEquals("OPEN", restaurantResponseDTO.getStatus());
        assertEquals("John Doe", restaurantResponseDTO.getOwnerName());
        assertEquals("image.png", restaurantResponseDTO.getImageUrl());
    }

    /**
     * Tests default constructor values
     */
    @Test
    void shouldCreateEmptyObject() {

        RestaurantResponseDTO restaurantResponseDTO = new RestaurantResponseDTO();

        assertNull(restaurantResponseDTO.getId());
        assertNull(restaurantResponseDTO.getName());
        assertNull(restaurantResponseDTO.getStatus());
        assertNull(restaurantResponseDTO.getOwnerName());
        assertNull(restaurantResponseDTO.getImageUrl());
    }
}