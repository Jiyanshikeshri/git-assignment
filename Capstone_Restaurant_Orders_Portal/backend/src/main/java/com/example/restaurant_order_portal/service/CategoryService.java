package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.CategoryRequestDTO;
import com.example.restaurant_order_portal.dto.CategoryResponseDTO;
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
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);

    /**
     * Get all categories belonging to a specific restaurant
     */
    List<CategoryResponseDTO> getCategoriesByRestaurant(Long restaurantId);

    /**
     * Update an existing category
     */
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO);

    /**
     * Delete a category by ID
     */
    void deleteCategory(Long id);
}
