package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.entity.Category;
import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.repository.CategoryRepository;
import com.example.restaurant_order_portal.repository.RestaurantRepository;
import com.example.restaurant_order_portal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of CategoryService interface.
 *
 * Contains business logic for managing categories
 * and ensures proper relationship mapping with Restaurant.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Creates a new category under a specific restaurant.
     *
     * Fetches the restaurant from DB
     */
    @Override
    public Category createCategory(Category category) {

        /**
         *  Extract restaurant ID from request
          */
        Long restaurantId = category.getRestaurant().getId();

        /**
         * Fetch actual restaurant from DB
          */
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        /**
         * Set fetched restaurant
          */
        category.setRestaurant(restaurant);

        return categoryRepository.save(category);
    }

    /**
     * Retrieves all categories for a given restaurant.
     */
    @Override
    public List<Category> getCategoriesByRestaurant(Long restaurantId) {

        return categoryRepository.findByRestaurantId(restaurantId);
    }

    /**
     * Updates an existing category.
     */
    @Override
    public Category updateCategory(Long id, Category category) {

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setName(category.getName());

        return categoryRepository.save(existingCategory);
    }

    /**
     * Deletes a category by ID.
     */
    @Override
    public void deleteCategory(Long id) {

        categoryRepository.deleteById(id);
    }
}
