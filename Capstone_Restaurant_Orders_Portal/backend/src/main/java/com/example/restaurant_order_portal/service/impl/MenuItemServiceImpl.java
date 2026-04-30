package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.MenuItemRequestDTO;
import com.example.restaurant_order_portal.dto.MenuItemResponseDTO;
import com.example.restaurant_order_portal.entity.Category;
import com.example.restaurant_order_portal.entity.MenuItem;
import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.CategoryRepository;
import com.example.restaurant_order_portal.repository.MenuItemRepository;
import com.example.restaurant_order_portal.repository.RestaurantRepository;
import com.example.restaurant_order_portal.service.MenuItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(MenuItemServiceImpl.class);

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

        log.info("Creating menu item: {}", menuItemRequestDTO.getName());

        Category category = categoryRepository.findById(menuItemRequestDTO.getCategoryId())
                .orElseThrow(() ->  {
                    log.error("Category not found with id: {}", menuItemRequestDTO.getCategoryId());
                    return new ResourceNotFoundException("Category not found");
                });

        Restaurant restaurant = restaurantRepository.findById(menuItemRequestDTO.getRestaurantId())
                .orElseThrow(() -> {
                    log.error("Restaurant not found with id: {}", menuItemRequestDTO.getRestaurantId());
                    return new ResourceNotFoundException("Restaurant not found");
                });

        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequestDTO.getName());
        menuItem.setPrice(menuItemRequestDTO.getPrice());
        menuItem.setCategory(category);
        menuItem.setRestaurant(restaurant);
        menuItem.setImageUrl(menuItemRequestDTO.getImageUrl());

        MenuItem saved = menuItemRepository.save(menuItem);

        log.info("Menu item created successfully with id: {}", saved.getId());

        return mapToResponseDTO(saved);
    }

    /**
     * Get menu items by restaurant
     */
    @Override
    public List<MenuItemResponseDTO> getMenuItemsByRestaurant(Long restaurantId) {
        log.info("Fetching menu items for restaurantId: {}", restaurantId);
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
        log.info("Fetching menu items for categoryId: {}", categoryId);
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
        log.info("Updating menu item with id: {}", id);

        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Menu item not found with id: {}", id);
                    return new ResourceNotFoundException("Menu item not found");
                });

        Category category = categoryRepository.findById(menuItemRequestDTO.getCategoryId())
                .orElseThrow(() -> {
                    log.error("Category not found with id: {}", menuItemRequestDTO.getCategoryId());
                    return new ResourceNotFoundException("Category not found");
                });

        Restaurant restaurant = restaurantRepository.findById(menuItemRequestDTO.getRestaurantId())
                .orElseThrow(() -> {
                    log.error("Restaurant not found with id: {}", menuItemRequestDTO.getRestaurantId());
                    return new ResourceNotFoundException("Restaurant not found");
                });

        existing.setName(menuItemRequestDTO.getName());
        existing.setPrice(menuItemRequestDTO.getPrice());
        existing.setCategory(category);
        existing.setRestaurant(restaurant);
        existing.setImageUrl(menuItemRequestDTO.getImageUrl());

        MenuItem updated = menuItemRepository.save(existing);

        log.info("Menu item updated successfully: {}", updated.getId());

        return mapToResponseDTO(updated);
    }

    /**
     * Delete menu item
     */
    @Override
    public void deleteMenuItem(Long id) {
        log.info("Deleting menu item with id: {}", id);

        if (!menuItemRepository.existsById(id)) {
            log.error("Menu item not found with id: {}", id);
            throw new ResourceNotFoundException("Menu item not found");
        }

        menuItemRepository.deleteById(id);
        log.info("Menu item deleted successfully: {}", id);
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
