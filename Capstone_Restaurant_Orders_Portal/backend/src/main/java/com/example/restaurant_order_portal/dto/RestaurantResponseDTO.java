package com.example.restaurant_order_portal.dto;

/**
 * DTO for sending Restaurant response
 */
public class RestaurantResponseDTO {

    private Long id;
    private String name;
    private String status;
    private String ownerName;

    public RestaurantResponseDTO() {}

    public RestaurantResponseDTO(Long id, String name, String status, String ownerName) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.ownerName = ownerName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
