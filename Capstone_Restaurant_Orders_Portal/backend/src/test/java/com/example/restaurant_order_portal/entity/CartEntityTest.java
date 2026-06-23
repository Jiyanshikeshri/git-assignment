package com.example.restaurant_order_portal.entity;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Cart entity.
 */
public class CartEntityTest {

    /**
     * Helper method to set private id using reflection
     */
    private void setId(Cart cart, Long id) {
        try {
            Field field = Cart.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(cart, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests constructor and getters
     */
    @Test
    void shouldCreateCartSuccessfully() {

        User user = new User();
        Restaurant restaurant = new Restaurant();

        Cart cart = new Cart(user, restaurant);

        assertEquals(user, cart.getUser());
        assertEquals(restaurant, cart.getRestaurant());
    }

    /**
     * Tests setters
     */
    @Test
    void shouldSetValuesCorrectly() {

        Cart cart = new Cart();

        User user = new User();
        Restaurant restaurant = new Restaurant();

        cart.setUser(user);
        cart.setRestaurant(restaurant);

        assertEquals(user, cart.getUser());
        assertEquals(restaurant, cart.getRestaurant());
    }

    /**
     * Tests equals method
     */
    @Test
    void shouldBeEqualWhenIdsMatch() {

        Cart cart1 = new Cart();
        Cart cart2 = new Cart();

        setId(cart1, 1L);
        setId(cart2, 1L);

        assertEquals(cart1, cart2);
    }

    /**
     * Tests not equal when ids differ
     */
    @Test
    void shouldNotBeEqualWhenIdsDifferent() {

        Cart cart1 = new Cart();
        Cart cart2 = new Cart();

        setId(cart1, 1L);
        setId(cart2, 2L);

        assertNotEquals(cart1, cart2);
    }

    /**
     * Tests hashCode consistency
     */
    @Test
    void shouldReturnSameHashCodeForSameObject() {

        Cart cart = new Cart();
        setId(cart, 1L);

        assertEquals(cart.hashCode(), cart.hashCode());
    }

    /**
     * Tests toString method
     */
    @Test
    void shouldContainIdInToString() {

        Cart cart = new Cart();
        setId(cart, 1L);

        String result = cart.toString();

        assertTrue(result.contains("1"));
    }
}