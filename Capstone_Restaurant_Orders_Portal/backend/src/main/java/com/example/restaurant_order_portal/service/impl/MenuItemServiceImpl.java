package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.MenuItemRequestDTO;
import com.example.restaurant_order_portal.dto.MenuItemResponseDTO;
import com.example.restaurant_order_portal.entity.Category;
import com.example.restaurant_order_portal.entity.MenuItem;
import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.repository.CategoryRepository;
import com.example.restaurant_order_portal.repository.MenuItemRepository;
import com.example.restaurant_order_portal.repository.RestaurantRepository;
import com.example.restaurant_order_portal.service.MenuItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of MenuItemService.
 *
 * Handles business logic for menu items.
 */
@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, CategoryRepository categoryRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.categoryRepository = categoryRepository;
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Create menu item
     */
    @Override
    public MenuItemResponseDTO createMenuItem(MenuItemRequestDTO menuItemRequestDTO) {
        // fetch full category
        Category category = categoryRepository.findById(menuItemRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // fetch full restaurant
        Restaurant restaurant = restaurantRepository.findById(menuItemRequestDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequestDTO.getName());
        menuItem.setPrice(menuItemRequestDTO.getPrice());
        menuItem.setCategory(category);
        menuItem.setRestaurant(restaurant);
        menuItem.setImageUrl(menuItemRequestDTO.getImageUrl());

        MenuItem saved = menuItemRepository.save(menuItem);

        return mapToResponseDTO(saved);
    }

    /**
     * Get menu items by restaurant
     */
    @Override
    public List<MenuItemResponseDTO> getMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId)
        .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get menu items by category
     */
    @Override
    public List<MenuItemResponseDTO> getMenuItemsByCategory(Long categoryId) {
        return menuItemRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update menu item
     */
    @Override
    public MenuItemResponseDTO updateMenuItem(Long id, MenuItemRequestDTO menuItemRequestDTO) {
        /**
         * To fetch existing item
         */
        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu Item not found"));

        /**
         * To Fetch existing category
         */
        Category category = categoryRepository.findById(menuItemRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        /**
         * To fetch restaurant
         */
        Restaurant restaurant = restaurantRepository.findById(menuItemRequestDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        existing.setName(menuItemRequestDTO.getName());
        existing.setPrice(menuItemRequestDTO.getPrice());
        existing.setCategory(category);
        existing.setRestaurant(restaurant);
        existing.setImageUrl(menuItemRequestDTO.getImageUrl());

        MenuItem updated = menuItemRepository.save(existing);

        return mapToResponseDTO(updated);
    }

    /**
     * Delete menu item
     */
    @Override
    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }

    /**
     * Mapper method from Entity to DTO
     */
    private MenuItemResponseDTO mapToResponseDTO(MenuItem item) {
        return new MenuItemResponseDTO(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getCategory().getName(),
                item.getRestaurant().getName(),
                item.getImageUrl()
        );
    }
}
