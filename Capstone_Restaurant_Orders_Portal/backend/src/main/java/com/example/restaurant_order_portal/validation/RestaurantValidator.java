package com.example.restaurant_order_portal.validation;

import com.example.restaurant_order_portal.dto.RestaurantRequestDTO;
import com.example.restaurant_order_portal.exception.BadRequestException;

public class RestaurantValidator {
    /**
     * Validate restaurant creation request
     */
    public static void validateCreate(RestaurantRequestDTO restaurantRequestDTO) {

        if (restaurantRequestDTO.getName() == null || restaurantRequestDTO.getName().isBlank()) {
            throw new BadRequestException("Restaurant name is required");
        }

        if (restaurantRequestDTO.getStatus() == null || restaurantRequestDTO.getStatus().isBlank()) {
            throw new BadRequestException("Restaurant status is required");
        }

        if (restaurantRequestDTO.getOwnerId() == null) {
            throw new BadRequestException("OwnerId is required");
        }
    }

    /**
     * Validate restaurant update request
     */
    public static void validateUpdate(RestaurantRequestDTO dto) {

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("Restaurant name is required");
        }

        if (dto.getStatus() == null || dto.getStatus().isBlank()) {
            throw new BadRequestException("Restaurant status is required");
        }
    }
}
