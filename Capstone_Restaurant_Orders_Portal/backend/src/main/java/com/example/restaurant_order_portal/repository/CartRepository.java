package com.example.restaurant_order_portal.repository;

import com.example.restaurant_order_portal.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for Cart entity.
 *
 * Provides queries related to user carts.
 */

public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Method to find cart by userId
      */
    Optional<Cart> findByUserId(Long userId);
}
