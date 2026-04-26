package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.constants.AppConstants;
import com.example.restaurant_order_portal.dto.MenuItemRequestDTO;
import com.example.restaurant_order_portal.dto.MenuItemResponseDTO;
import com.example.restaurant_order_portal.entity.MenuItem;
import com.example.restaurant_order_portal.service.MenuItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for MenuItem APIs.
 *
 * Handles CRUD operations for menu items.
 */
@RestController
@RequestMapping(AppConstants.BASE_MENU_ITEM_URL)
public class MenuItemController {

    /**
     * Service layer for handling MenuItem operations
     */
    private final MenuItemService menuItemService;

    /**
     * Constructor for MenuItemController.
     */
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    /**
     * Create menu item
     */
    @PostMapping
    public MenuItemResponseDTO createMenuItem(@RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        return menuItemService.createMenuItem(menuItemRequestDTO);
    }

    /**
     * Get menu items by restaurant
     */
    @GetMapping(AppConstants.GET_MENUITEM_BY_RESTAURANT + "{restaurantId}")
    public List<MenuItemResponseDTO> getByRestaurant(@PathVariable Long restaurantId) {
        return menuItemService.getMenuItemsByRestaurant(restaurantId);
    }

    /**
     * Get menu items by category
     */
    @GetMapping(AppConstants.GET_BY_CATEGORY + "{categoryId}")
    public List<MenuItemResponseDTO> getByCategory(@PathVariable Long categoryId) {
        return menuItemService.getMenuItemsByCategory(categoryId);
    }

    /**
     ** Update menu item
     */
    @PutMapping("/{id}")
    public MenuItemResponseDTO updateMenuItem(@PathVariable Long id, @RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        return menuItemService.updateMenuItem(id, menuItemRequestDTO);
    }

    /**
     ** Delete menu item
     */
    @DeleteMapping("/{id}")
    public String deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return "Menu Item deleted successfully";
    }
}

