package com.example.restaurant_order_portal.entity;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CartItem entity.
 */
public class CartItemEntityTest {

    /**
     * Helper method to set private id using reflection
     */
    private void setId(CartItem cartItem, Long id) {
        try {
            Field field = CartItem.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(cartItem, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests constructor
     */
    @Test
    void shouldCreateCartItemSuccessfully() {

        Cart cart = new Cart();
        MenuItem menuItem = new MenuItem();

        CartItem cartItem = new CartItem(cart, menuItem, 2);

        assertEquals(cart, cartItem.getCart());
        assertEquals(menuItem, cartItem.getMenuItem());
        assertEquals(2, cartItem.getQuantity());
    }

    /**
     * Tests setters
     */
    @Test
    void shouldSetValuesCorrectly() {

        CartItem cartItem = new CartItem();

        Cart cart = new Cart();
        MenuItem menuItem = new MenuItem();

        cartItem.setCart(cart);
        cartItem.setMenuItem(menuItem);
        cartItem.setQuantity(5);

        assertEquals(cart, cartItem.getCart());
        assertEquals(menuItem, cartItem.getMenuItem());
        assertEquals(5, cartItem.getQuantity());
    }

    /**
     * Tests equals when IDs match
     */
    @Test
    void shouldBeEqualWhenIdsMatch() {

        CartItem item1 = new CartItem();
        CartItem item2 = new CartItem();

        setId(item1, 1L);
        setId(item2, 1L);

        assertEquals(item1, item2);
    }

    /**
     * Tests not equal when IDs differ
     */
    @Test
    void shouldNotBeEqualWhenIdsDifferent() {

        CartItem item1 = new CartItem();
        CartItem item2 = new CartItem();

        setId(item1, 1L);
        setId(item2, 2L);

        assertNotEquals(item1, item2);
    }

    /**
     * Tests hashCode consistency
     */
    @Test
    void shouldReturnSameHashCodeForSameObject() {

        CartItem item = new CartItem();
        setId(item, 1L);

        assertEquals(item.hashCode(), item.hashCode());
    }

    /**
     * Tests toString contains id
     */
    @Test
    void shouldContainIdInToString() {

        CartItem item = new CartItem();
        setId(item, 1L);

        String result = item.toString();

        assertTrue(result.contains("1"));
    }
}