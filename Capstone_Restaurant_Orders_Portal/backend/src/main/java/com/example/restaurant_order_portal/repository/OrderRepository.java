package com.example.restaurant_order_portal.repository;

import com.example.restaurant_order_portal.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Order entity.
 *
 * Provides queries related to orders placed by users and managed by restaurants.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * To get all orders of a user
      */
    List<Order> findByUserId(Long userId);

    /**
     * To get all orders for a restaurant
      */
    List<Order> findByRestaurantId(Long restaurantId);
}
