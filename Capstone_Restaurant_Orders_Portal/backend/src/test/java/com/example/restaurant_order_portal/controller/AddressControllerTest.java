package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.dto.AddressRequestDTO;
import com.example.restaurant_order_portal.dto.AddressResponseDTO;
import com.example.restaurant_order_portal.exception.BadRequestException;
import com.example.restaurant_order_portal.service.AddressService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AddressController.
 */
@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    @Mock
    private Authentication authentication;

    private final String USER_EMAIL = "test@example.com";

    /**
     * Valid request should save address successfully.
     */
    @Test
    void shouldSaveAddressSuccessfully() {

        when(authentication.getName()).thenReturn(USER_EMAIL);

        AddressRequestDTO request = new AddressRequestDTO();
        request.setStreetAddress("Street 1");
        request.setCity("Bhopal");
        request.setPincode("462001");

        AddressResponseDTO response = new AddressResponseDTO();
        response.setId(1L);

        when(addressService.saveAddress(request, USER_EMAIL))
                .thenReturn(response);

        AddressResponseDTO result =
                addressController.saveAddress(request, authentication);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(addressService).saveAddress(request, USER_EMAIL);
    }

    /**
     * Should fail validation when street address is missing.
     */
    @Test
    void shouldFailWhenStreetAddressMissing() {

        AddressRequestDTO request = new AddressRequestDTO();
        request.setCity("Bhopal");
        request.setPincode("462001");

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> addressController.saveAddress(request, authentication)
        );

        assertEquals("Street address is required", ex.getMessage());

        verifyNoInteractions(addressService);
    }

    /**
     * Should return all user addresses.
     */
    @Test
    void shouldGetUserAddresses() {

        when(authentication.getName()).thenReturn(USER_EMAIL);

        AddressResponseDTO addr1 = new AddressResponseDTO();
        addr1.setId(1L);

        AddressResponseDTO addr2 = new AddressResponseDTO();
        addr2.setId(2L);

        when(addressService.getUserAddresses(USER_EMAIL))
                .thenReturn(List.of(addr1, addr2));

        List<AddressResponseDTO> result =
                addressController.getUserAddresses(authentication);

        assertEquals(2, result.size());

        verify(addressService).getUserAddresses(USER_EMAIL);
    }

    /**
     * Should update address successfully.
     */
    @Test
    void shouldUpdateAddressSuccessfully() {

        when(authentication.getName()).thenReturn(USER_EMAIL);

        Long addressId = 1L;

        AddressRequestDTO request = new AddressRequestDTO();
        request.setStreetAddress("Street 1");
        request.setCity("Indore");
        request.setPincode("452001");

        AddressResponseDTO response = new AddressResponseDTO();
        response.setId(addressId);
        response.setCity("Indore");

        when(addressService.updateAddress(addressId, request, USER_EMAIL))
                .thenReturn(response);

        AddressResponseDTO result =
                addressController.updateAddress(addressId, request, authentication);

        assertNotNull(result);
        assertEquals("Indore", result.getCity());

        verify(addressService).updateAddress(addressId, request, USER_EMAIL);
    }

    /**
     * Should delete address successfully.
     */
    @Test
    void shouldDeleteAddressSuccessfully() {

        when(authentication.getName()).thenReturn(USER_EMAIL);

        Long addressId = 1L;

        doNothing().when(addressService)
                .deleteAddress(addressId, USER_EMAIL);

        String result =
                addressController.deleteAddress(addressId, authentication);

        assertEquals("Address deleted successfully", result);

        verify(addressService).deleteAddress(addressId, USER_EMAIL);
    }

    /**
     * Should propagate service exception when address not found.
     */
    @Test
    void shouldThrowExceptionWhenAddressNotFound() {

        when(authentication.getName()).thenReturn(USER_EMAIL);

        AddressRequestDTO request = new AddressRequestDTO();
        request.setStreetAddress("Street 1");
        request.setCity("Bhopal");
        request.setPincode("462001");

        when(addressService.saveAddress(request, USER_EMAIL))
                .thenThrow(new RuntimeException("Address not found"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> addressController.saveAddress(request, authentication)
        );

        assertEquals("Address not found", ex.getMessage());
    }
}