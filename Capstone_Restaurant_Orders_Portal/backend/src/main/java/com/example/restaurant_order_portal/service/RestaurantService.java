package com.example.restaurant_order_portal.service;

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
    Restaurant createRestaurant(Restaurant restaurant);

    /**
     * Fetch all restaurants
     *
     * @return list of all restaurants
     */
    List<Restaurant> getAllRestaurants();

    /**
     * Get a restaurant by its ID
     * @return restaurant if found
     */
    Restaurant getRestaurantById(Long id);

    /**
     * Update restaurant details
     * @return updated restaurant
     */
    Restaurant updateRestaurant(Long id, Restaurant restaurant);

    /**
     * Delete a restaurant by ID
     */
    void deleteRestaurant(Long id);
}
