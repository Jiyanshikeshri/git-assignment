package com.example.restaurant_order_portal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for AddressResponseDTO
 */
public class AddressResponseDTOTest {

    /**
     * Test constructor
     */
    @Test
    void shouldCreateObjectUsingConstructor() {

        AddressResponseDTO addressResponseDTO =
                new AddressResponseDTO(1L, "Street 1", "Delhi", "110001");

        assertEquals(1L, addressResponseDTO.getId());
        assertEquals("Street 1", addressResponseDTO.getStreetAddress());
        assertEquals("Delhi", addressResponseDTO.getCity());
        assertEquals("110001", addressResponseDTO.getPincode());
    }

    /**
     * Test setters and getters
     */
    @Test
    void shouldSetAndGetFieldsCorrectly() {

        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();

        addressResponseDTO.setId(2L);
        addressResponseDTO.setStreetAddress("Street 2");
        addressResponseDTO.setCity("Mumbai");
        addressResponseDTO.setPincode("400001");

        assertEquals(2L, addressResponseDTO.getId());
        assertEquals("Street 2", addressResponseDTO.getStreetAddress());
        assertEquals("Mumbai", addressResponseDTO.getCity());
        assertEquals("400001", addressResponseDTO.getPincode());
    }

    /**
     * Test default values
     */
    @Test
    void shouldHaveNullValuesInitially() {

        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();

        assertNull(addressResponseDTO.getId());
        assertNull(addressResponseDTO.getStreetAddress());
        assertNull(addressResponseDTO.getCity());
        assertNull(addressResponseDTO.getPincode());
    }
}