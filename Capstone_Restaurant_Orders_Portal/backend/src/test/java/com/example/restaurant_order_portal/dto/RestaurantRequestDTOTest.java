package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for RestaurantRequestDTO.
 */
public class RestaurantRequestDTOTest {

    /**
     * Tests setting and getting all fields
     */
    @Test
    void shouldSetAndGetValues() {

        RestaurantRequestDTO restaurantRequestDTO = new RestaurantRequestDTO();

        restaurantRequestDTO.setName("Dominos");
        restaurantRequestDTO.setStatus("OPEN");
        restaurantRequestDTO.setOwnerId(1L);
        restaurantRequestDTO.setImageUrl("image.png");

        assertEquals("Dominos", restaurantRequestDTO.getName());
        assertEquals("OPEN", restaurantRequestDTO.getStatus());
        assertEquals(1L, restaurantRequestDTO.getOwnerId());
        assertEquals("image.png", restaurantRequestDTO.getImageUrl());
    }

    /**
     * Tests default constructor creates empty object
     */
    @Test
    void shouldCreateEmptyObject() {

        RestaurantRequestDTO restaurantRequestDTO = new RestaurantRequestDTO();

        assertNull(restaurantRequestDTO.getName());
        assertNull(restaurantRequestDTO.getStatus());
        assertNull(restaurantRequestDTO.getOwnerId());
        assertNull(restaurantRequestDTO.getImageUrl());
    }
}