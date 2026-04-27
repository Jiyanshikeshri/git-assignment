package com.example.restaurant_order_portal.dto;

import com.example.restaurant_order_portal.enums.OrderStatus;

import java.time.LocalDateTime;

/**
 * This DTO contains all relevant information about an order
 * after it is created or fetched.
 */
public class OrderResponseDTO {

    /**
     * Unique ID of the order
     */
    private Long id;

    /**
     * ID of the user who placed the order
     */
    private Long userId;

    /**
     * ID of the restaurant from which order is placed
     */
    private Long restaurantId;

    /**
     * Total amount of the order
     */
    private Double totalAmount;

    /**
     * Current status of the order
     */
    private OrderStatus status;

    /**
     * Timestamp when order was created
     */
    private LocalDateTime createdAt;

    public OrderResponseDTO() {

    }

    public OrderResponseDTO(Long id, Long userId, Long restaurantId, Double totalAmount, OrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
