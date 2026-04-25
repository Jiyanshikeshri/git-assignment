package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.entity.MenuItem;

import java.util.List;

/**
 * Service interface for MenuItem entity.
 *
 * Defines business operations related to menu items.
 */
public interface MenuItemService {
    /**
     * Create a new menu item
     */
    MenuItem createMenuItem(MenuItem menuItem);

    /**
     * Get all menu items by restaurant
     */
    List<MenuItem> getMenuItemsByRestaurant(Long restaurantId);

    /**
     * Get all menu items by category
     */
    List<MenuItem> getMenuItemsByCategory(Long categoryId);

    /**
     * Update menu item
     */
    MenuItem updateMenuItem(Long id, MenuItem menuItem);

    /**
     * Delete menu item
     */
    void deleteMenuItem(Long id);
}
