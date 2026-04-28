package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.CartRequestDTO;
import com.example.restaurant_order_portal.dto.CartResponseDTO;
import com.example.restaurant_order_portal.entity.Cart;
import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.repository.CartRepository;
import com.example.restaurant_order_portal.repository.RestaurantRepository;
import com.example.restaurant_order_portal.repository.UserRepository;
import com.example.restaurant_order_portal.service.CartService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of CartService.
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           UserRepository userRepository,
                           RestaurantRepository restaurantRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Creates a new cart for a user.
     */
    @Override
    public CartResponseDTO createCart(CartRequestDTO cartRequestDTO) {

        if (cartRequestDTO.getUserId() == null || cartRequestDTO.getRestaurantId() == null) {
            throw new RuntimeException("UserId and RestaurantId are required");
        }

        User user = userRepository.findById(cartRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(cartRequestDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Optional<Cart> existingCart = cartRepository.findByUserId(cartRequestDTO.getUserId());

        if (existingCart.isPresent()) {
            return mapToDTO(existingCart.get());
        }

        Cart cart = new Cart(user, restaurant);
        Cart savedCart = cartRepository.save(cart);

        return new CartResponseDTO(
                savedCart.getId(),
                user.getId(),
                restaurant.getId()
        );
    }

    /**
     * Retrieves cart by user ID.
     */
    @Override
    public CartResponseDTO getCartByUserId(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return new CartResponseDTO(
                cart.getId(),
                cart.getUser().getId(),
                cart.getRestaurant().getId()
        );
    }

    /**
     * Clears cart for a user.
     */
    @Override
    public void clearCart(Long userId) {

        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);

        cartOptional.ifPresent(cartRepository::delete);
    }

    private CartResponseDTO mapToDTO(Cart cart) {
        return new CartResponseDTO(
                cart.getId(),
                cart.getUser().getId(),
                cart.getRestaurant().getId()
        );
    }
}
