package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.dto.OrderRequestDTO;
import com.example.restaurant_order_portal.dto.OrderResponseDTO;
import com.example.restaurant_order_portal.service.OrderService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for OrderController.
 */
@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    /**
     * Mocked OrderService
     */
    @Mock
    private OrderService orderService;

    /**
     * Inject mocks into controller
     */
    @InjectMocks
    private OrderController orderController;

    /**
     * Create order successfully
     */
    @Test
    void shouldCreateOrderSuccessfully() {

        OrderRequestDTO request = new OrderRequestDTO();

        OrderResponseDTO response = new OrderResponseDTO();

        when(orderService.createOrder(request)).thenReturn(response);

        OrderResponseDTO result = orderController.createOrder(request);

        assertNotNull(result);

        verify(orderService).createOrder(request);
    }

    /**
     * Get orders by user
     */
    @Test
    void shouldGetOrdersByUser() {

        Long userId = 1L;

        OrderResponseDTO response = new OrderResponseDTO();

        when(orderService.getOrdersByUser(userId))
                .thenReturn(List.of(response));

        List<OrderResponseDTO> result =
                orderController.getOrdersByUser(userId);

        assertEquals(1, result.size());

        verify(orderService).getOrdersByUser(userId);
    }

    /**
     * Get orders by restaurant
     */
    @Test
    void shouldGetOrdersByRestaurant() {

        Long restaurantId = 10L;

        OrderResponseDTO response = new OrderResponseDTO();

        when(orderService.getOrdersByRestaurant(restaurantId))
                .thenReturn(List.of(response));

        List<OrderResponseDTO> result =
                orderController.getOrdersByRestaurant(restaurantId);

        assertEquals(1, result.size());

        verify(orderService).getOrdersByRestaurant(restaurantId);
    }

    /**
     * Cancel order successfully
     */
    @Test
    void shouldCancelOrderSuccessfully() {

        Long orderId = 1L;

        doNothing().when(orderService).cancelOrder(orderId);

        String result = orderController.cancelOrder(orderId);

        assertEquals("Order cancelled successfully and amount refunded", result);

        verify(orderService).cancelOrder(orderId);
    }

    /**
     * Exception when cancelling order
     */
    @Test
    void shouldThrowExceptionWhenCancelFails() {

        Long orderId = 1L;

        doThrow(new RuntimeException("Order cannot be cancelled"))
                .when(orderService).cancelOrder(orderId);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> orderController.cancelOrder(orderId)
        );

        assertEquals("Order cannot be cancelled", ex.getMessage());
    }
}