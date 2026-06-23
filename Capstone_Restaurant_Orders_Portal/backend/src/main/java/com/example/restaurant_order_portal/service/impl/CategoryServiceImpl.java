package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.CategoryRequestDTO;
import com.example.restaurant_order_portal.dto.CategoryResponseDTO;
import com.example.restaurant_order_portal.entity.Category;
import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.CategoryRepository;
import com.example.restaurant_order_portal.repository.MenuItemRepository;
import com.example.restaurant_order_portal.repository.RestaurantRepository;
import com.example.restaurant_order_portal.service.CategoryService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

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

        log.info("Creating category '{}' for restaurantId: {}", categoryRequestDTO.getName(), categoryRequestDTO.getRestaurantId());

        Restaurant restaurant = restaurantRepository.findById(categoryRequestDTO.getRestaurantId())
                .orElseThrow(() -> {
                    log.error("Restaurant not found with id: {}", categoryRequestDTO.getRestaurantId());
                    return new ResourceNotFoundException("Restaurant not found");
                });

        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        category.setImageUrl(categoryRequestDTO.getImageUrl());
        category.setRestaurant(restaurant);

        Category saved = categoryRepository.save(category);

        log.info("Category created successfully with id: {}", saved.getId());

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

        log.info("Fetching categories for restaurantId: {}", restaurantId);

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

        log.info("Updating category with id: {}", id);

        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Category not found with id: {}", id);
                    return new ResourceNotFoundException("Category not found");
                });

        existing.setName(categoryRequestDTO.getName());
        existing.setImageUrl(categoryRequestDTO.getImageUrl());

        Category updated = categoryRepository.save(existing);

        log.info("Category updated successfully: {}", updated.getId());

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

        log.info("Deleting category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Category not found with id: {}", id);
                    return new ResourceNotFoundException("Category not found");
                });

        /**
         * delete all menu items of this category first
         */
        menuItemRepository.deleteByCategoryId(id);
        log.info("Deleted menu items for categoryId: {}", id);

        /**
         * then delete category
         */
        categoryRepository.delete(category);
        log.info("Category deleted successfully: {}", id);
    }
}
