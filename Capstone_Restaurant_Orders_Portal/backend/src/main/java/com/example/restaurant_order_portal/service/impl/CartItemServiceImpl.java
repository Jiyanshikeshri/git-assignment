package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.CartItemRequestDTO;
import com.example.restaurant_order_portal.dto.CartItemResponseDTO;
import com.example.restaurant_order_portal.entity.Cart;
import com.example.restaurant_order_portal.entity.CartItem;
import com.example.restaurant_order_portal.entity.MenuItem;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.exception.ConflictException;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.CartItemRepository;
import com.example.restaurant_order_portal.repository.CartRepository;
import com.example.restaurant_order_portal.repository.MenuItemRepository;
import com.example.restaurant_order_portal.service.CartItemService;
import com.example.restaurant_order_portal.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of CartItemService
 */
@Service
public class CartItemServiceImpl implements CartItemService {

    private static final Logger log = LoggerFactory.getLogger(CartItemServiceImpl.class);

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

        log.info("Adding item to cart for userId: {}, menuItemId: {}", cartItemRequestDTO.getUserId(), cartItemRequestDTO.getMenuItemId());

        MenuItem menuItem = menuItemRepository.findById(cartItemRequestDTO.getMenuItemId())
                .orElseThrow(() -> {
                    log.error("Menu item not found with id: {}", cartItemRequestDTO.getMenuItemId());
                    return new ResourceNotFoundException("Menu item not found");
                });

        Optional<Cart> cartOptional = cartRepository.findByUserId(cartItemRequestDTO.getUserId());

        Cart cart;

        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
            } else {
            User user = userRepository.findById(cartItemRequestDTO.getUserId())
                    .orElseThrow(() -> {
                        log.error("User not found with id: {}", cartItemRequestDTO.getUserId());
                        return new ResourceNotFoundException("User not found");
                    });

            cart = new Cart();
            cart.setUser(user);
            cart.setRestaurant(menuItem.getRestaurant());

            cart = cartRepository.save(cart);
            log.info("New cart created for userId: {}", cartItemRequestDTO.getUserId());
        }

        if (cart.getRestaurant() == null) {
            cart.setRestaurant(menuItem.getRestaurant());
            cartRepository.save(cart);
        }

        if(!cart.getRestaurant().getId().equals(menuItem.getRestaurant().getId())) {
            log.error("User tried to add item from different restaurant");
            throw new ConflictException("Cannot add items from different restaurant");
        }

        // If item already exists in cart, instead of adding it again update the quantity
        Optional<CartItem> existingItem =
                cartItemRepository.findByCart_IdAndMenuItem_Id(cart.getId(), menuItem.getId());

        CartItem cartItem;

        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemRequestDTO.getQuantity());
            log.info("Updated quantity for existing cart item");
        } else {
            cartItem = new CartItem(cart, menuItem, cartItemRequestDTO.getQuantity());
            log.info("Added new item to cart");
        }

        CartItem saved = cartItemRepository.save(cartItem);

        CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO();

        cartItemResponseDTO.setCartItemId(saved.getId());
        cartItemResponseDTO.setCartId(cart.getId());
        cartItemResponseDTO.setMenuItemId(menuItem.getId());
        cartItemResponseDTO.setQuantity(saved.getQuantity());

        cartItemResponseDTO.setMenuItemName(menuItem.getName());
        cartItemResponseDTO.setPrice(menuItem.getPrice());

        log.info("Item successfully added to cart. cartItemId: {}", saved.getId());

        return cartItemResponseDTO;
    }

    /**
     * Gets all cart items.
     */
    @Override
    public List<CartItemResponseDTO> getCartItems(Long userId) {

        log.info("Fetching cart items for userId: {}", userId);

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("Cart not found for userId: {}", userId);
                    return new ResourceNotFoundException("Cart not found");
                }   );

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

        log.info("Removing cart item with id: {}", cartItemId);

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> {
            log.error("Cart item not found with id: {}", cartItemId);
            return new ResourceNotFoundException("Cart item not found");
        });

        cartItemRepository.delete(item);

        log.info("Cart item deleted successfully: {}", cartItemId);
    }
}
