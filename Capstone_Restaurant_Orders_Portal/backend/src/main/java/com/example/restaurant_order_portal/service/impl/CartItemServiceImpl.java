package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.CartItemRequestDTO;
import com.example.restaurant_order_portal.dto.CartItemResponseDTO;
import com.example.restaurant_order_portal.entity.Cart;
import com.example.restaurant_order_portal.entity.CartItem;
import com.example.restaurant_order_portal.entity.MenuItem;
import com.example.restaurant_order_portal.repository.CartItemRepository;
import com.example.restaurant_order_portal.repository.CartRepository;
import com.example.restaurant_order_portal.repository.MenuItemRepository;
import com.example.restaurant_order_portal.service.CartItemService;
import com.example.restaurant_order_portal.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of CartItemService
 */
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MenuItemRepository menuItemRepository;

    public CartItemServiceImpl(CartRepository cartRepository,
                               CartItemRepository cartItemRepository,
                               MenuItemRepository menuItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.menuItemRepository = menuItemRepository;
    }

    /**
     * Adds item to cart.
     */
    @Override
    public CartItemResponseDTO addItemToCart(CartItemRequestDTO cartItemRequestDTO) {

        Cart cart = cartRepository.findByUserId(cartItemRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        MenuItem menuItem = menuItemRepository.findById(cartItemRequestDTO.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        if (!cart.getRestaurant().getId().equals(menuItem.getRestaurant().getId())) {
            throw new RuntimeException("Cannot add items from different restaurant");
        }

        CartItem cartItem = new CartItem(cart, menuItem, cartItemRequestDTO.getQuantity());
        CartItem saved = cartItemRepository.save(cartItem);

        return new CartItemResponseDTO(
                saved.getId(),
                cart.getId(),
                menuItem.getId(),
                saved.getQuantity()
        );
    }

    /**
     * Gets all cart items.
     */
    @Override
    public List<CartItemResponseDTO> getCartItems(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartItemRepository.findByCartId(cart.getId())
                .stream()
                .map(item -> new CartItemResponseDTO(
                        item.getId(),
                        cart.getId(),
                        item.getMenuItem().getId(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Removes cart item.
     */
    @Override
    public void removeItem(Long cartItemId) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItemRepository.delete(item);
    }
}
