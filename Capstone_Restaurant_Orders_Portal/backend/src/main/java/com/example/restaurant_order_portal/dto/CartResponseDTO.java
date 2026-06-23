package com.example.restaurant_order_portal.dto;

/**
 * DTO for sending Cart details in response.
*/
public class CartResponseDTO {

    private Long cartId;
    private Long userId;
    private Long restaurantId;

    public CartResponseDTO() {

    }

    public CartResponseDTO(Long cartId, Long userId, Long restaurantId) {
        this.cartId = cartId;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    /**
     * Gets Cart ID
     */
    public Long getCartId() {
        return cartId;
    }

    /**
     * Sets Cart ID
     */
    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    /**
     * Gets User Id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets User ID
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
     * Sets Restaurant ID
     */
    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
