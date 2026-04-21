package com.example.restaurant_order_portal.repository;

import com.example.restaurant_order_portal.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // This will get all items in a cart
    List<CartItem> findByCartId(Long cartId);
}
