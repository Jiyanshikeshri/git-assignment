package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for OrderItemResponseDTO
 */
public class OrderItemResponseDTOTest {

    /**
     * Tests parameterized constructor and getters
     */
    @Test
    void shouldCreateObjectUsingConstructor() {

        OrderItemResponseDTO orderItemResponseDTO =
                new OrderItemResponseDTO(1L, 100L, 10L, 2, 200.0);

        assertEquals(1L, orderItemResponseDTO.getId());
        assertEquals(100L, orderItemResponseDTO.getOrderId());
        assertEquals(10L, orderItemResponseDTO.getMenuItemId());
        assertEquals(2, orderItemResponseDTO.getQuantity());
        assertEquals(200.0, orderItemResponseDTO.getPrice());
    }

    /**
     * Tests setter and getter methods
     */
    @Test
    void shouldSetAndGetValues() {

        OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();

        orderItemResponseDTO.setId(2L);
        orderItemResponseDTO.setOrderId(200L);
        orderItemResponseDTO.setMenuItemId(20L);
        orderItemResponseDTO.setQuantity(5);
        orderItemResponseDTO.setPrice(500.0);

        assertEquals(2L, orderItemResponseDTO.getId());
        assertEquals(200L, orderItemResponseDTO.getOrderId());
        assertEquals(20L, orderItemResponseDTO.getMenuItemId());
        assertEquals(5, orderItemResponseDTO.getQuantity());
        assertEquals(500.0, orderItemResponseDTO.getPrice());
    }

    /**
     * Tests default constructor
     */
    @Test
    void shouldCreateEmptyObject() {

        OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();

        assertNotNull(orderItemResponseDTO);
        assertNull(orderItemResponseDTO.getId());
        assertNull(orderItemResponseDTO.getOrderId());
        assertNull(orderItemResponseDTO.getMenuItemId());
        assertNull(orderItemResponseDTO.getQuantity());
        assertNull(orderItemResponseDTO.getPrice());
    }
}