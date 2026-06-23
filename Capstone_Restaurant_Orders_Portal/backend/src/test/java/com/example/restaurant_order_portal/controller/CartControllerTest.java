package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.dto.CartRequestDTO;
import com.example.restaurant_order_portal.dto.CartResponseDTO;
import com.example.restaurant_order_portal.service.CartService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CartController.
 */
@ExtendWith(MockitoExtension.class)
public class CartControllerTest {

    /**
     * Mocked CartService dependency.
     */
    @Mock
    private CartService cartService;

    /**
     * Injects mocked dependencies into CartController.
     */
    @InjectMocks
    private CartController cartController;

    /**
     * Tests successful cart creation.
     */
    @Test
    void shouldCreateCartSuccessfully() {

        CartRequestDTO request = new CartRequestDTO();
        request.setUserId(1L);

        CartResponseDTO response = new CartResponseDTO();
        response.setCartId(10L);

        when(cartService.createCart(request)).thenReturn(response);

        CartResponseDTO result = cartController.createCart(request);

        assertNotNull(result);
        assertEquals(10L, result.getCartId());

        verify(cartService).createCart(request);
    }

    /**
     * Tests fetching cart by user ID.
     */
    @Test
    void shouldGetCartByUserId() {

        Long userId = 1L;

        CartResponseDTO response = new CartResponseDTO();
        response.setCartId(10L);

        when(cartService.getCartByUserId(userId)).thenReturn(response);

        CartResponseDTO result = cartController.getCartByUserId(userId);

        assertNotNull(result);
        assertEquals(10L, result.getCartId());

        verify(cartService).getCartByUserId(userId);
    }

    /**
     * Tests clearing cart successfully.
     */
    @Test
    void shouldClearCartSuccessfully() {

        Long userId = 1L;

        doNothing().when(cartService).clearCart(userId);

        String result = cartController.clearCart(userId);

        assertEquals("Cart cleared successfully", result);

        verify(cartService).clearCart(userId);
    }

    /**
     * Tests behavior when service throws exception during cart creation.
     */
    @Test
    void shouldThrowExceptionWhenCreateCartFails() {

        CartRequestDTO request = new CartRequestDTO();
        request.setUserId(1L);

        when(cartService.createCart(request))
                .thenThrow(new RuntimeException("Cart creation failed"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> cartController.createCart(request)
        );

        assertEquals("Cart creation failed", ex.getMessage());
    }

    /**
     * Tests behavior when cart is not found.
     */
    @Test
    void shouldThrowExceptionWhenCartNotFound() {

        Long userId = 1L;

        when(cartService.getCartByUserId(userId))
                .thenThrow(new RuntimeException("Cart not found"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> cartController.getCartByUserId(userId)
        );

        assertEquals("Cart not found", ex.getMessage());
    }
}