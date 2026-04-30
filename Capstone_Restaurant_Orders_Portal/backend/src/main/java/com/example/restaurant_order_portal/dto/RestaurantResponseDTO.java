package com.example.restaurant_order_portal.dto;

/**
 * DTO for sending Restaurant response
 */
public class RestaurantResponseDTO {

    private Long id;
    private String name;
    private String status;
    private String ownerName;
    private String imageUrl;

    public RestaurantResponseDTO() {}

    public RestaurantResponseDTO(Long id, String name, String status, String ownerName, String imageUrl) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.ownerName = ownerName;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }
}
