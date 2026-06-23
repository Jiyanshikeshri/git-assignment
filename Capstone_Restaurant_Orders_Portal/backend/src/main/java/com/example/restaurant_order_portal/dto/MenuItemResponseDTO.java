package com.example.restaurant_order_portal.dto;

/**
 * DTO for returning MenuItem response
 */
public class MenuItemResponseDTO {

    private Long id;
    private String name;
    private Double price;
    private String categoryName;
    private String restaurantName;
    private String imageUrl;

    public MenuItemResponseDTO(Long id, String name, Double price,
                               String categoryName, String restaurantName, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
        this.restaurantName = restaurantName;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
