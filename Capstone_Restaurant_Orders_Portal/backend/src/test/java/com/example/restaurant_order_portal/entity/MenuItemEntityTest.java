package com.example.restaurant_order_portal.entity;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MenuItem entity.
 */
public class MenuItemEntityTest {

    /**
     * Helper to set private id using reflection
     */
    private void setId(MenuItem item, Long id) {
        try {
            Field field = MenuItem.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(item, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests constructor
     */
    @Test
    void shouldCreateMenuItemSuccessfully() {

        Category category = new Category();
        Restaurant restaurant = new Restaurant();

        MenuItem item = new MenuItem(
                "Pizza",
                250.0,
                category,
                restaurant,
                "img.png"
        );

        assertEquals("Pizza", item.getName());
        assertEquals(250.0, item.getPrice());
        assertEquals(category, item.getCategory());
        assertEquals(restaurant, item.getRestaurant());
        assertEquals("img.png", item.getImageUrl());
    }

    /**
     * Tests setters
     */
    @Test
    void shouldSetValuesCorrectly() {

        MenuItem item = new MenuItem();

        Category category = new Category();
        Restaurant restaurant = new Restaurant();

        item.setName("Burger");
        item.setPrice(150.0);
        item.setCategory(category);
        item.setRestaurant(restaurant);
        item.setImageUrl("burger.png");

        assertEquals("Burger", item.getName());
        assertEquals(150.0, item.getPrice());
        assertEquals(category, item.getCategory());
        assertEquals(restaurant, item.getRestaurant());
        assertEquals("burger.png", item.getImageUrl());
    }

    /**
     * Tests equals when IDs match
     */
    @Test
    void shouldBeEqualWhenIdsMatch() {

        MenuItem item1 = new MenuItem();
        MenuItem item2 = new MenuItem();

        setId(item1, 1L);
        setId(item2, 1L);

        assertEquals(item1, item2);
    }

    /**
     * Tests not equal when IDs differ
     */
    @Test
    void shouldNotBeEqualWhenIdsDifferent() {

        MenuItem item1 = new MenuItem();
        MenuItem item2 = new MenuItem();

        setId(item1, 1L);
        setId(item2, 2L);

        assertNotEquals(item1, item2);
    }

    /**
     * Tests hashCode consistency
     */
    @Test
    void shouldReturnSameHashCodeForSameObject() {

        MenuItem item = new MenuItem();
        setId(item, 1L);

        assertEquals(item.hashCode(), item.hashCode());
    }

    /**
     * Tests toString contains id
     */
    @Test
    void shouldContainIdInToString() {

        MenuItem item = new MenuItem();
        setId(item, 1L);

        String result = item.toString();

        assertTrue(result.contains("1"));
    }
}