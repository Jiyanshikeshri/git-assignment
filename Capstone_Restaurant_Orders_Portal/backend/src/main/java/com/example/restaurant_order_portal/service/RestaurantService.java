package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.RestaurantRequestDTO;
import com.example.restaurant_order_portal.dto.RestaurantResponseDTO;
import com.example.restaurant_order_portal.entity.Restaurant;

import java.util.List;

/**
 * Service interface for Restaurant module.
 *
 * This layer defines all business operations related to Restaurant.
 * Controller will call this, and implementation will handle logic.
 */
public interface RestaurantService {
    /**
     * Create a new restaurant
     */
    RestaurantResponseDTO createRestaurant(RestaurantRequestDTO restaurantRequestDTO);

    /**
     * Fetch all restaurants
     *
     * @return list of all restaurants
     */
    List<RestaurantResponseDTO> getAllRestaurants();

    /**
     * Get a restaurant by its ID
     * @return restaurant if found
     */
    RestaurantResponseDTO getRestaurantById(Long id);

    /**
     * Update restaurant details
     * @return updated restaurant
     */
    RestaurantResponseDTO updateRestaurant(Long id, RestaurantRequestDTO restaurantRequestDTO);

    /**
     * Delete a restaurant by ID
     */
    void deleteRestaurant(Long id);
}
