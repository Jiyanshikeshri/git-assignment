package com.example.restaurant_order_portal.entity;

import com.example.restaurant_order_portal.enums.RestaurantStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Restaurant entity.
 */
public class RestaurantEntityTest {

    /**
     * Tests constructor
     */
    @Test
    void shouldCreateRestaurantSuccessfully() {

        User owner = new User();

        Restaurant restaurant = new Restaurant(
                "Dominos",
                RestaurantStatus.OPEN,
                owner
        );

        assertEquals("Dominos", restaurant.getName());
        assertEquals(RestaurantStatus.OPEN, restaurant.getStatus());
        assertEquals(owner, restaurant.getOwner());
    }

    /**
     * Tests setters
     */
    @Test
    void shouldSetValuesCorrectly() {

        Restaurant restaurant = new Restaurant();
        User owner = new User();

        restaurant.setName("Pizza Hut");
        restaurant.setStatus(RestaurantStatus.CLOSED);
        restaurant.setOwner(owner);
        restaurant.setImageUrl("image.png");

        assertEquals("Pizza Hut", restaurant.getName());
        assertEquals(RestaurantStatus.CLOSED, restaurant.getStatus());
        assertEquals(owner, restaurant.getOwner());
        assertEquals("image.png", restaurant.getImageUrl());
    }

    /**
     * Tests equals when IDs match
     */
    @Test
    void shouldBeEqualWhenIdsMatch() {

        Restaurant r1 = new Restaurant();
        Restaurant r2 = new Restaurant();

        r1.setId(1L);
        r2.setId(1L);

        assertEquals(r1, r2);
    }

    /**
     * Tests not equal when IDs differ
     */
    @Test
    void shouldNotBeEqualWhenIdsDifferent() {

        Restaurant r1 = new Restaurant();
        Restaurant r2 = new Restaurant();

        r1.setId(1L);
        r2.setId(2L);

        assertNotEquals(r1, r2);
    }

    /**
     * Tests hashCode consistency
     */
    @Test
    void shouldReturnSameHashCodeForSameObject() {

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        assertEquals(restaurant.hashCode(), restaurant.hashCode());
    }

    /**
     * Tests toString contains id
     */
    @Test
    void shouldContainIdInToString() {

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        String result = restaurant.toString();

        assertTrue(result.contains("1"));
    }
}