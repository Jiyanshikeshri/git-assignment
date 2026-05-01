package com.example.restaurant_order_portal.entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Address entity.
 */
public class AddressEntityTest {

    /**
     * Tests constructor and getters
     */
    @Test
    void shouldCreateAddressSuccessfully() {

        User user = new User();
        Address address = new Address(user, "Street 1", "Delhi", "110001");

        assertEquals("Street 1", address.getStreetAddress());
        assertEquals("Delhi", address.getCity());
        assertEquals("110001", address.getPincode());
        assertEquals(user, address.getUser());
    }

    /**
     * Tests equals when IDs are same
     */
    @Test
    void shouldBeEqualWhenIdsMatch() {

        Address a1 = new Address();
        Address a2 = new Address();

        a1.setId(1L);
        a2.setId(1L);

        assertEquals(a1, a2);
    }

    /**
     * Tests equals when IDs are different
     */
    @Test
    void shouldNotBeEqualWhenIdsDifferent() {

        Address a1 = new Address();
        Address a2 = new Address();

        a1.setId(1L);
        a2.setId(2L);

        assertNotEquals(a1, a2);
    }

    /**
     * Tests equals when ID is null
     */
    @Test
    void shouldNotBeEqualWhenIdIsNull() {

        Address a1 = new Address();
        Address a2 = new Address();

        assertNotEquals(a1, a2);
    }

    /**
     * Tests hashCode consistency
     */
    @Test
    void shouldHaveSameHashCodeForSameId() {

        Address a1 = new Address();
        Address a2 = new Address();

        a1.setId(1L);
        a2.setId(1L);

        assertEquals(a1.hashCode(), a2.hashCode());
    }

    /**
     * Tests toString method
     */
    @Test
    void shouldReturnProperString() {

        Address address = new Address();
        address.setStreetAddress("Street 1");
        address.setCity("Delhi");
        address.setPincode("110001");

        String result = address.toString();

        assertTrue(result.contains("Street 1"));
        assertTrue(result.contains("Delhi"));
        assertTrue(result.contains("110001"));
    }
}
