package com.example.restaurant_order_portal.entity;

import com.example.restaurant_order_portal.enums.OrderStatus;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Order entity.
 */
public class OrderEntityTest {

    /**
     * Helper to set private id using reflection
     */
    private void setId(Order order, Long id) {
        try {
            Field field = Order.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(order, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests constructor
     */
    @Test
    void shouldCreateOrderSuccessfully() {

        User user = new User();
        Restaurant restaurant = new Restaurant();
        Address address = new Address();

        Order order = new Order(
                user,
                restaurant,
                500.0,
                OrderStatus.PLACED,
                address
        );

        assertEquals(user, order.getUser());
        assertEquals(restaurant, order.getRestaurant());
        assertEquals(500.0, order.getTotalAmount());
        assertEquals(OrderStatus.PLACED, order.getStatus());
        assertEquals(address, order.getAddress());
    }

    /**
     * Tests setters
     */
    @Test
    void shouldSetValuesCorrectly() {

        Order order = new Order();

        User user = new User();
        Restaurant restaurant = new Restaurant();
        Address address = new Address();

        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setTotalAmount(300.0);
        order.setStatus(OrderStatus.DELIVERED);
        order.setAddress(address);

        assertEquals(user, order.getUser());
        assertEquals(restaurant, order.getRestaurant());
        assertEquals(300.0, order.getTotalAmount());
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        assertEquals(address, order.getAddress());
    }

    /**
     * Tests PrePersist
     */
    @Test
    void shouldSetCreatedAtOnPersist() {

        Order order = new Order();

        order.onCreate(); // simulate JPA lifecycle

        assertNotNull(order.getCreatedAt());
        assertTrue(order.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    /**
     * Tests equals when IDs match
     */
    @Test
    void shouldBeEqualWhenIdsMatch() {

        Order o1 = new Order();
        Order o2 = new Order();

        setId(o1, 1L);
        setId(o2, 1L);

        assertEquals(o1, o2);
    }

    /**
     * Tests not equal when IDs differ
     */
    @Test
    void shouldNotBeEqualWhenIdsDifferent() {

        Order o1 = new Order();
        Order o2 = new Order();

        setId(o1, 1L);
        setId(o2, 2L);

        assertNotEquals(o1, o2);
    }

    /**
     * Tests hashCode consistency
     */
    @Test
    void shouldReturnSameHashCodeForSameObject() {

        Order order = new Order();
        setId(order, 1L);

        assertEquals(order.hashCode(), order.hashCode());
    }

    /**
     * Tests toString contains id
     */
    @Test
    void shouldContainIdInToString() {

        Order order = new Order();
        setId(order, 1L);

        String result = order.toString();

        assertTrue(result.contains("1"));
    }
}