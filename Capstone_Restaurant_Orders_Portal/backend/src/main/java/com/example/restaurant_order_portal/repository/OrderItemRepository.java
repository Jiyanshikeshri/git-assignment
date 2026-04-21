package com.example.restaurant_order_portal.repository;

import com.example.restaurant_order_portal.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // To get all items of a specific order
    List<OrderItem> findByOrderId(Long orderId);
}
