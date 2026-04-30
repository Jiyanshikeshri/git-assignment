package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.OrderRequestDTO;
import com.example.restaurant_order_portal.dto.OrderResponseDTO;

import java.util.List;

/**
 * Service interface for handling Order operations.
 *
 * Defines business logic methods related to orders.
 */
public interface OrderService {

    /**
     * Create a new order
     */
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    /**
     * Get all orders of a specific user
     */
    List<OrderResponseDTO> getOrdersByUser(Long userId);

    /**
     * Get all orders for a specific restaurant
     */
    List<OrderResponseDTO> getOrdersByRestaurant(Long restaurantId);

    /**
     * Cancel an order and refund wallet
     */
    void cancelOrder(Long orderId);
}
