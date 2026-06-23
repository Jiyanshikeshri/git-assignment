package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for OrderRequestDTO.
 */
public class OrderRequestDTOTest {

    /**
     * Tests parameterized constructor and getter
     */
    @Test
    void shouldCreateObjectUsingConstructor() {

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(10L);

        assertEquals(10L, orderRequestDTO.getAddressId());
    }

    /**
     * Tests setter and getter methods
     */
    @Test
    void shouldSetAndGetAddressId() {

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();

        orderRequestDTO.setAddressId(20L);

        assertEquals(20L, orderRequestDTO.getAddressId());
    }

    /**
     * Tests default constructor
     */
    @Test
    void shouldCreateEmptyObject() {

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();

        assertNotNull(orderRequestDTO);
        assertNull(orderRequestDTO.getAddressId());
    }
}