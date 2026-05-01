package com.example.restaurant_order_portal.entity;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for OrderItem entity.
 */
public class OrderItemEntityTest {

    /**
     * Helper to set private id using reflection
     */
    private void setId(OrderItem item, Long id) {
        try {
            Field field = OrderItem.class.getDeclaredField("id");
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
    void shouldCreateOrderItemSuccessfully() {

        Order order = new Order();
        MenuItem menuItem = new MenuItem();

        OrderItem item = new OrderItem(order, menuItem, 2, 300.0);

        assertEquals(order, item.getOrder());
        assertEquals(menuItem, item.getMenuItem());
        assertEquals(2, item.getQuantity());
        assertEquals(300.0, item.getPrice());
    }

    /**
     * Tests setters
     */
    @Test
    void shouldSetValuesCorrectly() {

        OrderItem item = new OrderItem();

        Order order = new Order();
        MenuItem menuItem = new MenuItem();

        item.setOrder(order);
        item.setMenuItem(menuItem);
        item.setQuantity(5);
        item.setPrice(150.0);

        assertEquals(order, item.getOrder());
        assertEquals(menuItem, item.getMenuItem());
        assertEquals(5, item.getQuantity());
        assertEquals(150.0, item.getPrice());
    }

    /**
     * Tests equals when IDs match
     */
    @Test
    void shouldBeEqualWhenIdsMatch() {

        OrderItem item1 = new OrderItem();
        OrderItem item2 = new OrderItem();

        setId(item1, 1L);
        setId(item2, 1L);

        assertEquals(item1, item2);
    }

    /**
     * Tests not equal when IDs differ
     */
    @Test
    void shouldNotBeEqualWhenIdsDifferent() {

        OrderItem item1 = new OrderItem();
        OrderItem item2 = new OrderItem();

        setId(item1, 1L);
        setId(item2, 2L);

        assertNotEquals(item1, item2);
    }

    /**
     * Tests hashCode consistency
     */
    @Test
    void shouldReturnSameHashCodeForSameObject() {

        OrderItem item = new OrderItem();
        setId(item, 1L);

        assertEquals(item.hashCode(), item.hashCode());
    }

    /**
     * Tests toString contains id
     */
    @Test
    void shouldContainIdInToString() {

        OrderItem item = new OrderItem();
        setId(item, 1L);

        String result = item.toString();

        assertTrue(result.contains("1"));
    }
}