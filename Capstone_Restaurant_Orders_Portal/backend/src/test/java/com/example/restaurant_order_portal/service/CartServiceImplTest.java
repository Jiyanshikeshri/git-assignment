package com.example.restaurant_order_portal.service;

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
import com.example.restaurant_order_portal.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private User user;
    private Restaurant restaurant;
    private Cart cart;
    private CartRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);

        restaurant = new Restaurant();
        ReflectionTestUtils.setField(restaurant, "id", 10L);

        cart = new Cart(user, restaurant);
        ReflectionTestUtils.setField(cart, "id", 5L);

        requestDTO = new CartRequestDTO();
        requestDTO.setUserId(1L);
        requestDTO.setRestaurantId(10L);
    }

    /**
     * Create cart
     */

    @Test
    void createCart_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartResponseDTO response = cartService.createCart(requestDTO);

        assertNotNull(response);
        assertEquals(1L, response.getUserId());
        assertEquals(10L, response.getRestaurantId());
    }

    @Test
    void createCart_existingCart() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));

        CartResponseDTO response = cartService.createCart(requestDTO);

        assertEquals(5L, response.getCartId());
    }

    @Test
    void createCart_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                cartService.createCart(requestDTO));
    }

    @Test
    void createCart_restaurantNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                cartService.createCart(requestDTO));
    }

    @Test
    void createCart_invalidInput() {
        CartRequestDTO badRequest = new CartRequestDTO();

        assertThrows(BadRequestException.class, () ->
                cartService.createCart(badRequest));
    }

    // ================= GET CART =================

    @Test
    void getCartByUserId_success() {
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));

        CartResponseDTO response = cartService.getCartByUserId(1L);

        assertEquals(5L, response.getCartId());
    }

    @Test
    void getCartByUserId_notFound() {
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                cartService.getCartByUserId(1L));
    }

    /**
     * Clear cart
     */

    @Test
    void clearCart_success() {
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));

        cartService.clearCart(1L);

        verify(cartRepository, times(1)).delete(cart);
    }

    @Test
    void clearCart_noCart() {
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.empty());

        cartService.clearCart(1L);

        verify(cartRepository, never()).delete(any());
    }
}