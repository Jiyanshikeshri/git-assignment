package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.dto.OrderItemResponseDTO;
import com.example.restaurant_order_portal.service.OrderItemService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for OrderItemController.
 */
@ExtendWith(MockitoExtension.class)
public class OrderItemControllerTest {

    /**
     * Mocked service layer
     */
    @Mock
    private OrderItemService orderItemService;

    /**
     * Controller with injected mocks
     */
    @InjectMocks
    private OrderItemController orderItemController;

    /**
     * Fetch order items successfully
     */
    @Test
    void shouldGetOrderItemsSuccessfully() {

        Long orderId = 1L;

        OrderItemResponseDTO response = new OrderItemResponseDTO();

        when(orderItemService.getOrderItemsByOrderId(orderId))
                .thenReturn(List.of(response));

        List<OrderItemResponseDTO> result =
                orderItemController.getOrderItems(orderId);

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(orderItemService).getOrderItemsByOrderId(orderId);
    }

    /**
     * Exception when order not found
     */
    @Test
    void shouldThrowExceptionWhenOrderNotFound() {

        Long orderId = 1L;

        when(orderItemService.getOrderItemsByOrderId(orderId))
                .thenThrow(new RuntimeException("Order not found"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> orderItemController.getOrderItems(orderId)
        );

        assertEquals("Order not found", ex.getMessage());
    }
}