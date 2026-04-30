package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.constants.AppConstants;
import com.example.restaurant_order_portal.dto.CategoryRequestDTO;
import com.example.restaurant_order_portal.dto.CategoryResponseDTO;
import com.example.restaurant_order_portal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for managing Category APIs.
 *
 * Handles category creation, retrieval, update,
 * and deletion operations.
 */
@RestController
@RequestMapping(AppConstants.BASE_CATEGORY_URL)
public class CategoryController {

    /**
     * Service layer for handling category operations
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * Create a new category under a restaurant
     */
    @PostMapping(AppConstants.CREATE_CATEGORY)
    public CategoryResponseDTO createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {

        return categoryService.createCategory(categoryRequestDTO);
    }

    /**
     * Get all categories for a specific restaurant
     */
    @GetMapping(AppConstants.GET_BY_RESTAURANT)
    public List<CategoryResponseDTO> getCategoriesByRestaurant(
            @PathVariable Long restaurantId) {

        return categoryService.getCategoriesByRestaurant(restaurantId);
    }

    /**
     * Update a category
     */
    @PutMapping(AppConstants.UPDATE_CATEGORY)
    public CategoryResponseDTO updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequestDTO categoryRequestDTO) {

        return categoryService.updateCategory(id, categoryRequestDTO);
    }

    /**
     * Delete a category
     */
    @DeleteMapping(AppConstants.DELETE_CATEGORY)
    public String deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);
        return "Category deleted successfully";
    }
}
