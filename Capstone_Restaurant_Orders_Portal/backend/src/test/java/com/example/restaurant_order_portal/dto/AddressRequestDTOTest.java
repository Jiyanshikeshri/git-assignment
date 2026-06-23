package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
/**
 * Unit tests for AddressRequestDTO.
 */
public class AddressRequestDTOTest {

    /**
     * Test setters and getters
     */
    @Test
    void shouldSetAndGetFieldsCorrectly() {

        AddressRequestDTO addressRequestDTO = new AddressRequestDTO();

        addressRequestDTO.setStreetAddress("Street 1");
        addressRequestDTO.setCity("Delhi");
        addressRequestDTO.setPincode("110001");

        assertEquals("Street 1", addressRequestDTO.getStreetAddress());
        assertEquals("Delhi", addressRequestDTO.getCity());
        assertEquals("110001", addressRequestDTO.getPincode());
    }

    /**
     * Test default object values
     */
    @Test
    void shouldHaveNullValuesInitially() {

        AddressRequestDTO addressRequestDTO = new AddressRequestDTO();

        assertNull(addressRequestDTO.getStreetAddress());
        assertNull(addressRequestDTO.getCity());
        assertNull(addressRequestDTO.getPincode());
    }
}