package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.CartItemRequestDTO;
import com.example.restaurant_order_portal.dto.CartItemResponseDTO;
import com.example.restaurant_order_portal.entity.*;
import com.example.restaurant_order_portal.exception.ConflictException;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.CartItemRepository;
import com.example.restaurant_order_portal.repository.CartRepository;
import com.example.restaurant_order_portal.repository.MenuItemRepository;
import com.example.restaurant_order_portal.repository.UserRepository;
import com.example.restaurant_order_portal.service.impl.CartItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CartItemServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    private User user;
    private MenuItem menuItem;
    private Cart cart;
    private CartItem cartItem;
    private CartItemRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);

        menuItem = new MenuItem();
        ReflectionTestUtils.setField(menuItem, "id", 10L);

        Restaurant restaurant = new Restaurant();
        ReflectionTestUtils.setField(restaurant, "id", 100L);

        menuItem.setRestaurant(restaurant);
        menuItem.setName("Pizza");
        menuItem.setPrice(200.0);

        cart = new Cart();
        ReflectionTestUtils.setField(cart, "id", 5L);
        cart.setUser(user);
        cart.setRestaurant(restaurant);

        cartItem = new CartItem(cart, menuItem, 2);
        ReflectionTestUtils.setField(cartItem, "id", 50L);

        requestDTO = new CartItemRequestDTO();
        requestDTO.setUserId(1L);
        requestDTO.setMenuItemId(10L);
        requestDTO.setQuantity(1);
    }

    /**
     * Adding an item
     */

    @Test
    void addItemToCart_newCart_success() {
        when(menuItemRepository.findById(10L)).thenReturn(Optional.of(menuItem));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        CartItemResponseDTO response = cartItemService.addItemToCart(requestDTO);

        assertNotNull(response);
        assertEquals(10L, response.getMenuItemId());
    }

    @Test
    void addItemToCart_existingCart_success() {
        when(menuItemRepository.findById(10L)).thenReturn(Optional.of(menuItem));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCart_IdAndMenuItem_Id(5L, 10L))
                .thenReturn(Optional.empty());
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        CartItemResponseDTO response = cartItemService.addItemToCart(requestDTO);

        assertTrue(response.getQuantity() >= 1);
    }

    @Test
    void addItemToCart_updateQuantity_existingItem() {
        when(menuItemRepository.findById(10L)).thenReturn(Optional.of(menuItem));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCart_IdAndMenuItem_Id(5L, 10L))
                .thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        CartItemResponseDTO response = cartItemService.addItemToCart(requestDTO);

        assertTrue(response.getQuantity() >= 2);
    }

    @Test
    void addItemToCart_menuItemNotFound() {
        when(menuItemRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                cartItemService.addItemToCart(requestDTO));
    }

    @Test
    void addItemToCart_differentRestaurant() {
        Restaurant anotherRestaurant = new Restaurant();
        ReflectionTestUtils.setField(anotherRestaurant, "id", 999L);

        menuItem.setRestaurant(anotherRestaurant);

        when(menuItemRepository.findById(10L)).thenReturn(Optional.of(menuItem));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));

        assertThrows(ConflictException.class, () ->
                cartItemService.addItemToCart(requestDTO));
    }

    /**
     * Get cart items
     */

    @Test
    void getCartItems_success() {
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartId(5L)).thenReturn(List.of(cartItem));

        List<CartItemResponseDTO> result = cartItemService.getCartItems(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getCartItems_cartNotFound() {
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                cartItemService.getCartItems(1L));
    }

    /**
     * Remove items
     */

    @Test
    void removeItem_success() {
        when(cartItemRepository.findById(50L)).thenReturn(Optional.of(cartItem));

        cartItemService.removeItem(50L);

        verify(cartItemRepository, times(1)).delete(cartItem);
    }

    @Test
    void removeItem_notFound() {
        when(cartItemRepository.findById(50L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->cartItemService.removeItem(50L));
    }
}

