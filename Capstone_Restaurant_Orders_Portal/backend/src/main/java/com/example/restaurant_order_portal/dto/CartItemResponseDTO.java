package com.example.restaurant_order_portal.dto;


/**
 * DTO for returning cart item details.
 */
public class CartItemResponseDTO {

    private Long cartItemId;
    private Long cartId;
    private Long menuItemId;
    private Integer quantity;
    private String menuItemName;
    private Double price;

    public CartItemResponseDTO() {}

    public CartItemResponseDTO(Long cartItemId, Long cartId, Long menuItemId, Integer quantity, String menuItemName, Double price) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.menuItemName = menuItemName;
        this.price = price;
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

    /**
     * Gets the name of the menu item
     */
    public String getMenuItemName() {
        return menuItemName;
    }

    /**
     * Gets the price of the menu item
     */
    public Double getPrice() {
        return price;
    }

    /**
     * to set the cart item id
     */
    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    /**
     * to set the cart id
     */
    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    /**
     * to set the menu item id
     */
    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    /**
     * to set the quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * to set the menu item name
     */
    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    /**
     * to set the price
     */
    public void setPrice(Double price) {
        this.price = price;
    }
}
