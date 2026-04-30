package com.example.restaurant_order_portal.dto;

/**
 * Data Transfer Object used to receive order creation request from client.
 */
public class OrderRequestDTO {

    private Long addressId;

    public OrderRequestDTO() {

    }

    public OrderRequestDTO(Long addressId) {
        this.addressId = addressId;
    }

    /**
     * to get the address id
     */
    public Long getAddressId() {
        return addressId;
    }

    /**
     * To set the address id
     */
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
