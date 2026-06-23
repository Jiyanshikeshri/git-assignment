package com.example.restaurant_order_portal.repository;

import com.example.restaurant_order_portal.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Category entity.
 *
 * Provides queries related to food categories within a restaurant.
 */

public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Custom method to find all categories of a particular restaurant
      */
    List<Category> findByRestaurantId(Long restaurantId);
}
