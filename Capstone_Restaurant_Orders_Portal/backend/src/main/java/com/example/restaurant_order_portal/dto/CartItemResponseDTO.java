package com.example.restaurant_order_portal.dto;


/**
 * DTO for returning cart item details.
 */
public class CartItemResponseDTO {

    private Long cartItemId;
    private Long cartId;
    private Long menuItemId;
    private Integer quantity;

    public CartItemResponseDTO() {}

    public CartItemResponseDTO(Long cartItemId, Long cartId, Long menuItemId, Integer quantity) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
    }

    /**
     * Gets cart item ID.
     */
    public Long getCartItemId() {
        return cartItemId;
    }

    /**
     * Gets cart ID.
     */
    public Long getCartId() {
        return cartId;
    }

    /**
     * Gets menu item ID.
     */
    public Long getMenuItemId() {
        return menuItemId;
    }

    /**
     * Gets quantity.
     */
    public Integer getQuantity() {
        return quantity;
    }
}
