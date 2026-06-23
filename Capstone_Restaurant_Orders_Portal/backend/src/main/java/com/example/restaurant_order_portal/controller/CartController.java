package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.constants.AppConstants;
import com.example.restaurant_order_portal.dto.CartRequestDTO;
import com.example.restaurant_order_portal.dto.CartResponseDTO;
import com.example.restaurant_order_portal.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for Cart operations.
 */
@RestController
@RequestMapping(AppConstants.BASE_CART_URL)
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * API to create a cart.
     */
    @PostMapping(AppConstants.CREATE_CART)
    public CartResponseDTO createCart(@RequestBody CartRequestDTO requestDTO) {
        return cartService.createCart(requestDTO);
    }

    /**
     * API to get cart by user ID.
     */
    @GetMapping(AppConstants.GET_CART_BY_USER)
    public CartResponseDTO getCartByUserId(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }

    /**
     * API to clear cart.
     */
    @DeleteMapping(AppConstants.CLEAR_CART)
    public String clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return "Cart cleared successfully";
    }

}
