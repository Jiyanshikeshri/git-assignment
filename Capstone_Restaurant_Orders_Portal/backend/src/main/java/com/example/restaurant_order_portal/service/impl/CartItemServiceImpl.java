package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.CartItemRequestDTO;
import com.example.restaurant_order_portal.dto.CartItemResponseDTO;
import com.example.restaurant_order_portal.entity.Cart;
import com.example.restaurant_order_portal.entity.CartItem;
import com.example.restaurant_order_portal.entity.MenuItem;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.repository.CartItemRepository;
import com.example.restaurant_order_portal.repository.CartRepository;
import com.example.restaurant_order_portal.repository.MenuItemRepository;
import com.example.restaurant_order_portal.service.CartItemService;
import com.example.restaurant_order_portal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of CartItemService
 */
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;

    public CartItemServiceImpl(CartRepository cartRepository,
                               CartItemRepository cartItemRepository,
                               MenuItemRepository menuItemRepository,
                               UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.menuItemRepository = menuItemRepository;
        this.userRepository = userRepository;
    }

    /**
     * Adds item to cart.
     */
    @Override
    public CartItemResponseDTO addItemToCart(CartItemRequestDTO cartItemRequestDTO) {

        MenuItem menuItem = menuItemRepository.findById(cartItemRequestDTO.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        Optional<Cart> cartOptional = cartRepository.findByUserId(cartItemRequestDTO.getUserId());

        Cart cart;

        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
        } else {
            User user = userRepository.findById(cartItemRequestDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            cart = new Cart();
            cart.setUser(user);
            cart.setRestaurant(menuItem.getRestaurant());

            cart = cartRepository.save(cart);
        }

        if (cart.getRestaurant() == null) {
            cart.setRestaurant(menuItem.getRestaurant());
            cartRepository.save(cart);
        }

        if (!cart.getRestaurant().getId().equals(menuItem.getRestaurant().getId())) {
            throw new RuntimeException("Cannot add items from different restaurant");
        }

        // If item already exists in cart, instead of adding it again update the quantity
        Optional<CartItem> existingItem =
                cartItemRepository.findByCart_IdAndMenuItem_Id(cart.getId(), menuItem.getId());

        CartItem cartItem;

        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemRequestDTO.getQuantity());
        } else {
            cartItem = new CartItem(cart, menuItem, cartItemRequestDTO.getQuantity());
        }

        CartItem saved = cartItemRepository.save(cartItem);

        CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO();

        cartItemResponseDTO.setCartItemId(saved.getId());
        cartItemResponseDTO.setCartId(cart.getId());
        cartItemResponseDTO.setMenuItemId(menuItem.getId());
        cartItemResponseDTO.setQuantity(saved.getQuantity());

        cartItemResponseDTO.setMenuItemName(menuItem.getName());
        cartItemResponseDTO.setPrice(menuItem.getPrice());

        return cartItemResponseDTO;
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
                .map(item -> {

                    CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO();

                    cartItemResponseDTO.setCartItemId(item.getId());
                    cartItemResponseDTO.setCartId(cart.getId());
                    cartItemResponseDTO.setMenuItemId(item.getMenuItem().getId());
                    cartItemResponseDTO.setQuantity(item.getQuantity());

                    MenuItem menuItem = item.getMenuItem();

                    cartItemResponseDTO.setMenuItemName(menuItem.getName());
                    cartItemResponseDTO.setPrice(menuItem.getPrice());

                    return cartItemResponseDTO;
                })
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
