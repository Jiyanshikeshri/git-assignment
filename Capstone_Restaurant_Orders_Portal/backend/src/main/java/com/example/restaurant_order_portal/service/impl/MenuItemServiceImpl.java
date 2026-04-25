package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.entity.MenuItem;
import com.example.restaurant_order_portal.repository.MenuItemRepository;
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

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    /**
     * Create menu item
     */
    @Override
    public MenuItem createMenuItem(MenuItem menuItem) {
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
