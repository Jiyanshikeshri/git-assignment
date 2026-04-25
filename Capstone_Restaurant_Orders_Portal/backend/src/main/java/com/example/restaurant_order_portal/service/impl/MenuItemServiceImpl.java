package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.entity.Category;
import com.example.restaurant_order_portal.entity.MenuItem;
import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.repository.CategoryRepository;
import com.example.restaurant_order_portal.repository.MenuItemRepository;
import com.example.restaurant_order_portal.repository.RestaurantRepository;
import com.example.restaurant_order_portal.service.MenuItemService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public MenuItem createMenuItem(MenuItem menuItem) {
        // fetch full category
        Category category = categoryRepository.findById(menuItem.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // fetch full restaurant
        Restaurant restaurant = restaurantRepository.findById(menuItem.getRestaurant().getId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        menuItem.setCategory(category);
        menuItem.setRestaurant(restaurant);
        return menuItemRepository.save(menuItem);
    }

    /**
     * Get menu items by restaurant
     */
    @Override
    public List<MenuItem> getMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    /**
     * Get menu items by category
     */
    @Override
    public List<MenuItem> getMenuItemsByCategory(Long categoryId) {
        return menuItemRepository.findByCategoryId(categoryId);
    }

    /**
     * Update menu item
     */
    @Override
    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu Item not found"));

        existing.setName(menuItem.getName());
        existing.setPrice(menuItem.getPrice());
        existing.setCategory(menuItem.getCategory());
        existing.setRestaurant(menuItem.getRestaurant());

        return menuItemRepository.save(existing);
    }

    /**
     * Delete menu item
     */
    @Override
    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}
