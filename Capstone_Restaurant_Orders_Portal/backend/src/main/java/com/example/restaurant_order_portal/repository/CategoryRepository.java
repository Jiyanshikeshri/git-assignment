package com.example.restaurant_order_portal.repository;

import com.example.restaurant_order_portal.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Custom method to find all categories of a particular restaurant
    List<Category> findByRestaurantId(Long restaurantId);
}
