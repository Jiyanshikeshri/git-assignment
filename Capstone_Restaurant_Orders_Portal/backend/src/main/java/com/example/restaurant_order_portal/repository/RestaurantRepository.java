package com.example.restaurant_order_portal.repository;

import com.example.restaurant_order_portal.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // Method to find all restaurants of a specific owner
    List<Restaurant> findByOwnerId(Long ownerId);
}
