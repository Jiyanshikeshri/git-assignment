package com.example.restaurant_order_portal.enums;

/**
 * Enum representing different states of an order.
 *
 * This is used to track the lifecycle of an order
 * from placement to completion or cancellation.
 */
public enum OrderStatus {
    PLACED,
    PENDING,
    DELIVERED,
    COMPLETED,
    CANCELLED
}
