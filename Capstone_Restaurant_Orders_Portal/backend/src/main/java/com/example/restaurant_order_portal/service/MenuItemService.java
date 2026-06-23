package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.MenuItemRequestDTO;
import com.example.restaurant_order_portal.dto.MenuItemResponseDTO;

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
    MenuItemResponseDTO createMenuItem(MenuItemRequestDTO menuItemRequestDTO);

    /**
     * Get all menu items by restaurant
     */
    List<MenuItemResponseDTO> getMenuItemsByRestaurant(Long restaurantId);

    /**
     * Get all menu items by category
     */
    List<MenuItemResponseDTO> getMenuItemsByCategory(Long categoryId);

    /**
     * Update menu item
     */
    MenuItemResponseDTO updateMenuItem(Long id, MenuItemRequestDTO menuItemRequestDTO);

    /**
     * Delete menu item
     */
    void deleteMenuItem(Long id);
}
