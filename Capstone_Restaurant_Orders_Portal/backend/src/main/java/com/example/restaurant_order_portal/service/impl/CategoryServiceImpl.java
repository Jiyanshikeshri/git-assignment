package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.CategoryRequestDTO;
import com.example.restaurant_order_portal.dto.CategoryResponseDTO;
import com.example.restaurant_order_portal.entity.Category;
import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.repository.CategoryRepository;
import com.example.restaurant_order_portal.repository.MenuItemRepository;
import com.example.restaurant_order_portal.repository.RestaurantRepository;
import com.example.restaurant_order_portal.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

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

    @Autowired
    private MenuItemRepository menuItemRepository;

    /**
     * Creates a new category under a specific restaurant.
     *
     * Fetches the restaurant from DB
     */
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {

        Restaurant restaurant = restaurantRepository.findById(categoryRequestDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        category.setImageUrl(categoryRequestDTO.getImageUrl());
        category.setRestaurant(restaurant);

        Category saved = categoryRepository.save(category);

        return new CategoryResponseDTO(
                saved.getId(),
                saved.getName(),
                restaurant.getId(),
                restaurant.getName(),
                saved.getImageUrl()
        );
    }

    /**
     * Retrieves all categories for a given restaurant.
     */
    @Override
    public List<CategoryResponseDTO> getCategoriesByRestaurant(Long restaurantId) {

        return categoryRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(cat -> new CategoryResponseDTO(
                        cat.getId(),
                        cat.getName(),
                        cat.getRestaurant().getId(),
                        cat.getRestaurant().getName(),
                        cat.getImageUrl()
                ))
                .toList();
    }

    /**
     * Updates an existing category.
     */
    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {

        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existing.setName(categoryRequestDTO.getName());
        existing.setImageUrl(categoryRequestDTO.getImageUrl());

        Category updated = categoryRepository.save(existing);

        return new CategoryResponseDTO(
                updated.getId(),
                updated.getName(),
                updated.getRestaurant().getId(),
                updated.getRestaurant().getName(),
                updated.getImageUrl()
        );
    }

    /**
     * Deletes a category by ID.
     */
    @Override
    @Transactional
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        /**
         * delete all menu items of this category first
         */
        menuItemRepository.deleteByCategoryId(id);

        /**
         * then delete category
         */
        categoryRepository.delete(category);
    }
}
