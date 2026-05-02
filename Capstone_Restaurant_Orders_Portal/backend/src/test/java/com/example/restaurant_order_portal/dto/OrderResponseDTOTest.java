package com.example.restaurant_order_portal.dto;

import com.example.restaurant_order_portal.enums.OrderStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for OrderResponseDTO.
 */
public class OrderResponseDTOTest {

    /**
     * Tests constructor
     */
    @Test
    void shouldCreateObjectUsingConstructor() {

        LocalDateTime now = LocalDateTime.now();

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(
                1L, 2L, 3L, 500.0,
                OrderStatus.PLACED,
                now,
                "Delhi"
        );

        assertEquals(1L, orderResponseDTO.getId());
        assertEquals(2L, orderResponseDTO.getUserId());
        assertEquals(3L, orderResponseDTO.getRestaurantId());
        assertEquals(500.0, orderResponseDTO.getTotalAmount());
        assertEquals(OrderStatus.PLACED, orderResponseDTO.getStatus());
        assertEquals(now, orderResponseDTO.getCreatedAt());
        assertEquals("Delhi", orderResponseDTO.getAddress());
    }

    /**
     * Tests setters and getters
     */
    @Test
    void shouldSetAndGetValues() {

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();

        LocalDateTime now = LocalDateTime.now();

        orderResponseDTO.setId(10L);
        orderResponseDTO.setUserId(20L);
        orderResponseDTO.setRestaurantId(30L);
        orderResponseDTO.setTotalAmount(1000.0);
        orderResponseDTO.setStatus(OrderStatus.DELIVERED);
        orderResponseDTO.setCreatedAt(now);
        orderResponseDTO.setAddress("Mumbai");

        assertEquals(10L, orderResponseDTO.getId());
        assertEquals(20L, orderResponseDTO.getUserId());
        assertEquals(30L, orderResponseDTO.getRestaurantId());
        assertEquals(1000.0, orderResponseDTO.getTotalAmount());
        assertEquals(OrderStatus.DELIVERED, orderResponseDTO.getStatus());
        assertEquals(now, orderResponseDTO.getCreatedAt());
        assertEquals("Mumbai", orderResponseDTO.getAddress());
    }
}