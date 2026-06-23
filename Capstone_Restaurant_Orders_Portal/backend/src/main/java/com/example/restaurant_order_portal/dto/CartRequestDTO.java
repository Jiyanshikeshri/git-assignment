package com.example.restaurant_order_portal.dto;

/**
 * DTO for creating or updating a Cart.
 */
public class CartRequestDTO {

    private Long userId;
    private Long restaurantId;

    public CartRequestDTO() {

    }

    public CartRequestDTO(Long userId, Long restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    /**
     * Gets user ID.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets userID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets Restaurant ID
     */
    public Long getRestaurantId() {
        return restaurantId;
    }

    /**
     * Sets Restaurant Id
     */
    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
