package com.example.restaurant_order_portal.dto;

/**
 * DTO for adding item to order
 */
public class OrderItemRequestDTO {
    private Long orderId;
    private Long menuItemId;
    private Integer quantity;

    public OrderItemRequestDTO() {

    }

    public OrderItemRequestDTO(Long orderId, Long menuItemId, Integer quantity) {
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
    }

    /**
     * Gets Order ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * Gets MenuItem ID
     */
    public Long getMenuItemId() {
        return menuItemId;
    }

    /**
     * Gets Quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets OrderID
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * Sets MenuItem Id
     */
    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    /**
     * Sets quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
