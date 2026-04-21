package com.example.restaurant_order_portal.repository;

import com.example.restaurant_order_portal.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    // Method to find all menu items for a particular restaurant
    List<MenuItemRepository> findByRestaurantId(Long restaurantId);

    // Method to find all menu items for a particular category
    List<MenuItemRepository> findByCategoryId(Long categoryId);
}
