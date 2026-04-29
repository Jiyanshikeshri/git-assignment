package com.example.restaurant_order_portal.dto;

/**
 * DTO for creating/updating Category
 */
public class CategoryRequestDTO {

    private String name;
    private Long restaurantId;
    private String imageUrl;

    public CategoryRequestDTO() {}

    public String getName() {
        return name;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
