package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.CartItemRequestDTO;
import com.example.restaurant_order_portal.dto.CartItemResponseDTO;

import java.util.List;

/**
 * Service interface for CartItem operations.
 */
public interface CartItemService {
    /**
     * Adds item to cart.
     */
    CartItemResponseDTO addItemToCart(CartItemRequestDTO cartItemRequestDTO);

    /**
     * Gets all items of a user's cart.
     */
    List<CartItemResponseDTO> getCartItems(Long userId);

    /**
     * Removes item from cart.
     */
    void removeItem(Long cartItemId);
}
