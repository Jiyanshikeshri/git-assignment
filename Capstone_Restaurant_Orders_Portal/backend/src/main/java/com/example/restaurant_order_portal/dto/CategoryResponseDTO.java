package com.example.restaurant_order_portal.dto;

/**
 * DTO for returning Category response
 */
public class CategoryResponseDTO {

    private Long id;
    private String name;
    private Long restaurantId;
    private String restaurantName;

    public CategoryResponseDTO(Long id, String name, Long restaurantId, String restaurantName) {
        this.id = id;
        this.name = name;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
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
}
