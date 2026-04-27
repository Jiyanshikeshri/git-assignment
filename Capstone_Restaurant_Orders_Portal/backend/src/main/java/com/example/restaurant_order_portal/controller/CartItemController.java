package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.constants.AppConstants;
import com.example.restaurant_order_portal.dto.CartItemRequestDTO;
import com.example.restaurant_order_portal.dto.CartItemResponseDTO;
import com.example.restaurant_order_portal.service.CartItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for CartItem operations.
 */
@RestController
@RequestMapping(AppConstants.BASE_CART_ITEM_URL)
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    /**
     * Add item to cart.
     */
    @PostMapping(AppConstants.ADD_CART_ITEM)
    public CartItemResponseDTO addItem(@RequestBody CartItemRequestDTO requestDTO) {
        return cartItemService.addItemToCart(requestDTO);
    }

    /**
     * Get all cart items.
     */
    @GetMapping(AppConstants.GET_CART_ITEMS)
    public List<CartItemResponseDTO> getItems(@PathVariable Long userId) {
        return cartItemService.getCartItems(userId);
    }

    /**
     * Remove cart item.
     */
    @DeleteMapping(AppConstants.REMOVE_CART_ITEM)
    public String removeItem(@PathVariable Long cartItemId) {
        cartItemService.removeItem(cartItemId);
        return "Cart item removed successfully";
    }
}
