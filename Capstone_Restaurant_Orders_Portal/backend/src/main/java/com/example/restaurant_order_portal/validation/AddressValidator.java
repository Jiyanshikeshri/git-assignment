package com.example.restaurant_order_portal.validation;

import com.example.restaurant_order_portal.dto.AddressRequestDTO;
import com.example.restaurant_order_portal.exception.BadRequestException;

public class AddressValidator {
    /**
     * Validate address request data
     */
    public static void validate(AddressRequestDTO request) {

        if (request.getStreetAddress() == null || request.getStreetAddress().isBlank()) {
            throw new BadRequestException("Street address is required");
        }

        if (request.getCity() == null || request.getCity().isBlank()) {
            throw new BadRequestException("City is required");
        }

        if (request.getPincode() == null || request.getPincode().isBlank()) {
            throw new BadRequestException("Pincode is required");
        }
    }
}
