package com.example.restaurant_order_portal.dto;

/**
 * Data Transfer Object used to receive order creation request from client.
 */
public class OrderRequestDTO {
    /**
     * ID of the user placing the order
     */
    private Long userId;

    /**
     * ID of the restaurant from which order is placed
     */
    private Long restaurantId;

    public OrderRequestDTO() {

    }

    public OrderRequestDTO(Long userId, Long restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
