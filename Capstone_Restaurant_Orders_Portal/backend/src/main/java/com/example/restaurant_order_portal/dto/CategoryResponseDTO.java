package com.example.restaurant_order_portal.dto;

/**
 * DTO for returning Category response
 */
public class CategoryResponseDTO {

    private Long id;
    private String name;
    private Long restaurantId;
    private String restaurantName;
    private String imageUrl;

    public CategoryResponseDTO(Long id, String name, Long restaurantId, String restaurantName, String imageUrl) {
        this.id = id;
        this.name = name;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
