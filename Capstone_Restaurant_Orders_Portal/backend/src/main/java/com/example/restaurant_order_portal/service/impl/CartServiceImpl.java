package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.CartRequestDTO;
import com.example.restaurant_order_portal.dto.CartResponseDTO;
import com.example.restaurant_order_portal.entity.Cart;
import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.exception.BadRequestException;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.CartRepository;
import com.example.restaurant_order_portal.repository.RestaurantRepository;
import com.example.restaurant_order_portal.repository.UserRepository;
import com.example.restaurant_order_portal.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of CartService.
 */
@Service
public class CartServiceImpl implements CartService {

    private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

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

        log.info("Creating cart for userId: {}, restaurantId: {}",
                cartRequestDTO.getUserId(), cartRequestDTO.getRestaurantId());

        if (cartRequestDTO.getUserId() == null || cartRequestDTO.getRestaurantId() == null) {
            log.error("UserId or RestaurantId is null");
            throw new BadRequestException("UserId and RestaurantId are required");
        }

        User user = userRepository.findById(cartRequestDTO.getUserId())
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", cartRequestDTO.getUserId());
                    return new ResourceNotFoundException("User not found");
                });

        Restaurant restaurant = restaurantRepository.findById(cartRequestDTO.getRestaurantId())
                .orElseThrow(() -> {
                    log.error("Restaurant not found with id: {}", cartRequestDTO.getRestaurantId());
                    return new ResourceNotFoundException("Restaurant not found");
                });

        Optional<Cart> existingCart = cartRepository.findByUserId(cartRequestDTO.getUserId());

        if (existingCart.isPresent()) {
            log.info("Cart already exists for userId: {}", cartRequestDTO.getUserId());
            return mapToDTO(existingCart.get());
        }

        Cart cart = new Cart(user, restaurant);
        Cart savedCart = cartRepository.save(cart);

        log.info("Cart created successfully with id: {}", savedCart.getId());

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

        log.info("Fetching cart for userId: {}", userId);

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("Cart not found for userId: {}", userId);
                    return new ResourceNotFoundException("Cart not found");
                });

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

        log.info("Clearing cart for userId: {}", userId);

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
