package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for OrderItemRequestDTO.
 */
public class OrderItemRequestDTOTest {

    /**
     * Tests parameterized constructor and getters
     */
    @Test
    void shouldCreateObjectUsingConstructor() {

        OrderItemRequestDTO orderItemRequestDTO =
                new OrderItemRequestDTO(1L, 10L, 2);

        assertEquals(1L, orderItemRequestDTO.getOrderId());
        assertEquals(10L, orderItemRequestDTO.getMenuItemId());
        assertEquals(2, orderItemRequestDTO.getQuantity());
    }

    /**
     * Tests setter and getter methods
     */
    @Test
    void shouldSetAndGetValues() {

        OrderItemRequestDTO orderItemRequestDTO = new OrderItemRequestDTO();

        orderItemRequestDTO.setOrderId(2L);
        orderItemRequestDTO.setMenuItemId(20L);
        orderItemRequestDTO.setQuantity(5);

        assertEquals(2L, orderItemRequestDTO.getOrderId());
        assertEquals(20L, orderItemRequestDTO.getMenuItemId());
        assertEquals(5, orderItemRequestDTO.getQuantity());
    }

    /**
     * Tests default constructor
     */
    @Test
    void shouldCreateEmptyObject() {

        OrderItemRequestDTO orderItemRequestDTO = new OrderItemRequestDTO();

        assertNotNull(orderItemRequestDTO);
        assertNull(orderItemRequestDTO.getOrderId());
        assertNull(orderItemRequestDTO.getMenuItemId());
        assertNull(orderItemRequestDTO.getQuantity());
    }
}