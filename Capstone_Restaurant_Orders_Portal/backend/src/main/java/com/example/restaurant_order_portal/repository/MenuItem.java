package com.example.restaurant_order_portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItem extends JpaRepository<MenuItem, Long> {

    // Method to find all menu items for a particular restaurant
    List<MenuItem> findByRestaurantId(Long restaurantId);

    // Method to find all menu items for a particular category
    List<MenuItem> findByCategoryId(Long categoryId);
}
