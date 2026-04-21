package com.example.restaurant_order_portal.repository;

import com.example.restaurant_order_portal.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // To get all orders of a user
    List<Order> findByUserId(Long userId);

    // To get all orders for a restaurant
    List<Order> findByRestaurantId(Long restaurantId);
}
