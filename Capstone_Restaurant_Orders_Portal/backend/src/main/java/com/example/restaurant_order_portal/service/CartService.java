package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.CartRequestDTO;
import com.example.restaurant_order_portal.dto.CartResponseDTO;

/**
 * Service interface for Cart operations.
 *
 * Defines business logic related to cart management.
 */
public interface CartService {

    /**
     * Creates a new cart for a user.
     */
    CartResponseDTO createCart(CartRequestDTO cartRequestDTO);

    /**
     * Retrieves cart by user ID.
     */
    CartResponseDTO getCartByUserId(Long userId);

    /**
     * Clears cart (used when order is placed).
     */
    void clearCart(Long userId);
}
