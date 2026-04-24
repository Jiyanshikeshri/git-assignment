package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.repository.RestaurantRepository;
import com.example.restaurant_order_portal.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of RestaurantService.
 *
 * Contains actual business logic for Restaurant operations.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

        @Autowired
        private RestaurantRepository restaurantRepository;

        /**
         * Create a new restaurant
         */
        @Override
        public Restaurant createRestaurant(Restaurant restaurant) {
            return restaurantRepository.save(restaurant);
        }

        /**
         * Fetch all restaurants
         */
        @Override
        public List<Restaurant> getAllRestaurants() {
            return restaurantRepository.findAll();
        }

        /**
         * Get restaurant by ID
         */
        @Override
        public Restaurant getRestaurantById(Long id) {

            // Optional used to handle null safely
            Optional<Restaurant> restaurant = restaurantRepository.findById(id);

            if (restaurant.isPresent()) {
                return restaurant.get();
            } else {
                throw new RuntimeException("Restaurant not found with id: " + id);
            }
        }

        /**
         * Update restaurant details
         */
        @Override
        public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {

            Restaurant existingRestaurant = getRestaurantById(id);

            // Update fields
            existingRestaurant.setName(updatedRestaurant.getName());
            existingRestaurant.setStatus(updatedRestaurant.getStatus());

            return restaurantRepository.save(existingRestaurant);
        }

        /**
         * Delete restaurant
         */
        @Override
        public void deleteRestaurant(Long id) {

            Restaurant restaurant = getRestaurantById(id);

            restaurantRepository.delete(restaurant);
        }

}
