package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.dto.CartItemRequestDTO;
import com.example.restaurant_order_portal.dto.CartItemResponseDTO;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.UserRepository;
import com.example.restaurant_order_portal.service.CartItemService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CartItemController.
 */
@ExtendWith(MockitoExtension.class)
public class CartItemControllerTest {

    /**
     * Mocked CartItemService.
     */
    @Mock
    private CartItemService cartItemService;

    /**
     * Mocked UserRepository.
     */
    @Mock
    private UserRepository userRepository;

    /**
     * Inject mocks into controller.
     */
    @InjectMocks
    private CartItemController cartItemController;

    /**
     * Mocked Authentication.
     */
    @Mock
    private Authentication authentication;

    private final String USER_EMAIL = "test@example.com";

    /**
     * Tests adding item to cart successfully.
     */
    @Test
    void shouldAddItemSuccessfully() {

        CartItemRequestDTO request = new CartItemRequestDTO();

        CartItemResponseDTO response = new CartItemResponseDTO();

        when(cartItemService.addItemToCart(request)).thenReturn(response);

        CartItemResponseDTO result = cartItemController.addItem(request);

        assertNotNull(result);

        verify(cartItemService).addItemToCart(request);
    }

    /**
     * Tests fetching cart items for logged-in user.
     */
    @Test
    void shouldGetCartItems() {

        when(authentication.getName()).thenReturn(USER_EMAIL);

        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);

        when(userRepository.findByEmail(USER_EMAIL))
                .thenReturn(Optional.of(user));

        when(cartItemService.getCartItems(1L))
                .thenReturn(List.of(new CartItemResponseDTO()));

        List<CartItemResponseDTO> result =
                cartItemController.getItems(authentication);

        assertEquals(1, result.size());

        verify(userRepository).findByEmail(USER_EMAIL);
        verify(cartItemService).getCartItems(1L);
    }

    /**
     * Tests exception when user is not found.
     */
    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(authentication.getName()).thenReturn(USER_EMAIL);

        when(userRepository.findByEmail(USER_EMAIL))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> cartItemController.getItems(authentication)
        );

        assertEquals("User not found", ex.getMessage());

        verify(userRepository).findByEmail(USER_EMAIL);
        verifyNoInteractions(cartItemService);
    }

    /**
     * Tests removing cart item successfully.
     */
    @Test
    void shouldRemoveItemSuccessfully() {

        Long cartItemId = 1L;

        doNothing().when(cartItemService).removeItem(cartItemId);

        String result = cartItemController.removeItem(cartItemId);

        assertEquals("Cart item removed successfully", result);

        verify(cartItemService).removeItem(cartItemId);
    }

    /**
     * Tests exception when removing item fails.
     */
    @Test
    void shouldThrowExceptionWhenRemoveFails() {

        Long cartItemId = 1L;

        doThrow(new RuntimeException("Item not found"))
                .when(cartItemService).removeItem(cartItemId);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> cartItemController.removeItem(cartItemId)
        );

        assertEquals("Item not found", ex.getMessage());
    }
}