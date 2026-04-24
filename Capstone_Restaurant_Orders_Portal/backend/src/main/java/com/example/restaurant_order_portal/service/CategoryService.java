package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.entity.Category;

import java.util.List;

/**
 * Service interface for Category operations.
 *
 * Defines business logic methods for managing categories
 * under a specific restaurant.
 */
public interface CategoryService {
    /**
     * Create a new category under a restaurant
     */
    Category createCategory(Category category);

    /**
     * Get all categories belonging to a specific restaurant
     */
    List<Category> getCategoriesByRestaurant(Long restaurantId);

    /**
     * Update an existing category
     */
    Category updateCategory(Long id, Category category);

    /**
     * Delete a category by ID
     */
    void deleteCategory(Long id);
}
