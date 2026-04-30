package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.AddressRequestDTO;
import com.example.restaurant_order_portal.dto.AddressResponseDTO;

import java.util.List;

/**
 * Service interface for managing user addresses.
 *
 * Defines all operations related to address creation,
 * retrieval, update, and deletion.
 */
public interface AddressService {

    /**
     * Saves a new address for the logged-in user.
     */
    AddressResponseDTO saveAddress(AddressRequestDTO addressRequestDTO, String email);

    /**
     * Retrieves all addresses for a specific user.
     */
    List<AddressResponseDTO> getUserAddresses(String email);

    /**
     * Updates an existing address.
     */
    AddressResponseDTO updateAddress(Long addressId, AddressRequestDTO addressRequestDTO, String email);

    /**
     * Deletes an address for the logged-in user.
     */
    void deleteAddress(Long addressId, String email);
}
