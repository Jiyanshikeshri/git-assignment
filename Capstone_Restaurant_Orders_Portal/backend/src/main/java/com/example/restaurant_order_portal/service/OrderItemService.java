package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.OrderItemResponseDTO;

import java.util.List;

/**
 * Service for handling order item operations
 */
public class OrderItemService {
    /**
     * Get all items of an order
     */
    List<OrderItemResponseDTO> getOrderItemsByOrderId(Long orderId);
}
