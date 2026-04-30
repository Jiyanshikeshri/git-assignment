package com.example.restaurant_order_portal.repository;

import com.example.restaurant_order_portal.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for MenuItem entity.
 *
 * Provides queries related to menu items in restaurants.
 */

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    /**
     * Method to find all menu items for a particular restaurant
      */
    List<MenuItem> findByRestaurantId(Long restaurantId);

    /**
     * Method to find all menu items for a particular category
      */
    List<MenuItem> findByCategoryId(Long categoryId);

    /**
     * To delete all menuItems inside a specific category, if category is getting deleted
     */
    void deleteByCategoryId(Long categoryId);
}
