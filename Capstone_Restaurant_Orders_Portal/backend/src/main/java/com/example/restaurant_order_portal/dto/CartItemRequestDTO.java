package com.example.restaurant_order_portal.dto;

/**
 * DTO for adding/updating item in cart.
 *
 * It Contains user, menu item and quantity details.
 */
public class CartItemRequestDTO {
    private Long userId;
    private Long menuItemId;
    private Integer quantity;

    public CartItemRequestDTO() {
        
    }

    public CartItemRequestDTO(Long userId, Long menuItemId, Integer quantity) {
        this.userId = userId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
    }

    /**
     * Gets user ID.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets user ID.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets menu item ID.
     */
    public Long getMenuItemId() {
        return menuItemId;
    }

    /**
     * Sets menu item ID.
     */
    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    /**
     * Gets quantity.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

