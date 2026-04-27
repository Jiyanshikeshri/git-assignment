package com.example.restaurant_order_portal.repository;

import com.example.restaurant_order_portal.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for CartItem entity.
 *
 * Provides queries related to items inside a cart.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    /**
     *  This will get all items in a cart
     */
    List<CartItem> findByCartId(Long cartId);

    /**
     * Find cart item by cart and menu item.
     */
    Optional<CartItem> findByCartIdAndMenuItemId(Long cartId, Long menuItemId);
}
