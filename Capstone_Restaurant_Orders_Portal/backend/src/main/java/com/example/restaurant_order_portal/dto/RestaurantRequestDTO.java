package com.example.restaurant_order_portal.dto;

/**
 * DTO for creating/updating Restaurant
 */
public class RestaurantRequestDTO {

    private String name;
    private String status;
    private Long ownerId;
    private String imageUrl;

    public RestaurantRequestDTO() {}

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
