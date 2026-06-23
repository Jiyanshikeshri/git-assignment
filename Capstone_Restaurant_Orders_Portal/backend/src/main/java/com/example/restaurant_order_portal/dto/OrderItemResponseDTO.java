package com.example.restaurant_order_portal.dto;

/**
 * DTO for sending order item details
 */
public class OrderItemResponseDTO {

    private Long id;
    private Long orderId;
    private Long menuItemId;
    private Integer quantity;
    private Double price;

    public OrderItemResponseDTO() {

    }

    public OrderItemResponseDTO(Long id, Long orderId, Long menuItemId, Integer quantity, Double price) {
        this.id = id;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Get the order item id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the order item id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the order id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     *  Sets the Order ID
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the MenuItemId
     */
    public Long getMenuItemId() {
        return menuItemId;
    }

    /**
     * Sets the menuItemId
     */
    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    /**
     * Gets the quantity of the order item
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     *Sets the quantity of order items
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price of the orderItems
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price of the Order Items
     */
    public void setPrice(Double price) {
        this.price = price;
    }
}
