package com.example.restaurant_order_portal.entity;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Category entity.
 */
public class CategoryEntityTest {

    /**
     * Helper method to set private id using reflection
     */
    private void setId(Category category, Long id) {
        try {
            Field field = Category.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(category, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests constructor
     */
    @Test
    void shouldCreateCategorySuccessfully() {

        Restaurant restaurant = new Restaurant();

        Category category = new Category("Drinks", restaurant);

        assertEquals("Drinks", category.getName());
        assertEquals(restaurant, category.getRestaurant());
    }

    /**
     * Tests setters
     */
    @Test
    void shouldSetValuesCorrectly() {

        Category category = new Category();

        Restaurant restaurant = new Restaurant();

        category.setName("Desserts");
        category.setRestaurant(restaurant);
        category.setImageUrl("image.png");

        assertEquals("Desserts", category.getName());
        assertEquals(restaurant, category.getRestaurant());
        assertEquals("image.png", category.getImageUrl());
    }

    /**
     * Tests equals when IDs match
     */
    @Test
    void shouldBeEqualWhenIdsMatch() {

        Category c1 = new Category();
        Category c2 = new Category();

        setId(c1, 1L);
        setId(c2, 1L);

        assertEquals(c1, c2);
    }

    /**
     * Tests not equal when IDs differ
     */
    @Test
    void shouldNotBeEqualWhenIdsDifferent() {

        Category c1 = new Category();
        Category c2 = new Category();

        setId(c1, 1L);
        setId(c2, 2L);

        assertNotEquals(c1, c2);
    }

    /**
     * Tests hashCode consistency
     */
    @Test
    void shouldReturnSameHashCodeForSameObject() {

        Category category = new Category();
        setId(category, 1L);

        assertEquals(category.hashCode(), category.hashCode());
    }

    /**
     * Tests toString contains id
     */
    @Test
    void shouldContainIdInToString() {

        Category category = new Category();
        setId(category, 1L);

        String result = category.toString();

        assertTrue(result.contains("1"));
    }
}