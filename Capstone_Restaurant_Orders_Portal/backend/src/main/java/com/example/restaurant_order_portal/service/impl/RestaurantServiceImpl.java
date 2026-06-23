package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.RestaurantRequestDTO;
import com.example.restaurant_order_portal.dto.RestaurantResponseDTO;
import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.enums.RestaurantStatus;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.RestaurantRepository;
import com.example.restaurant_order_portal.repository.UserRepository;
import com.example.restaurant_order_portal.service.RestaurantService;
import com.example.restaurant_order_portal.validation.RestaurantValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of RestaurantService.
 *
 * Contains actual business logic for Restaurant operations.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

        private static final Logger log = LoggerFactory.getLogger(RestaurantServiceImpl.class);

        private final RestaurantRepository restaurantRepository;
        private final UserRepository userRepository;

        public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                  UserRepository userRepository) {
            this.restaurantRepository = restaurantRepository;
            this.userRepository = userRepository;
        }

        /**
         * Convert Entity to ResponseDTO
         */
        private RestaurantResponseDTO restaurantResponseDTO(Restaurant restaurant) {
            return new RestaurantResponseDTO(
                    restaurant.getId(),
                    restaurant.getName(),
                    restaurant.getStatus().toString(),
                    restaurant.getOwner() != null
                            ? restaurant.getOwner().getFirstName()
                            : null,
                    restaurant.getImageUrl()
            );
        }

        /**
         * DTO to Entity
         */
        private Restaurant mapToEntity(RestaurantRequestDTO restaurantRequestDTO) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(restaurantRequestDTO.getName());
            restaurant.setStatus(RestaurantStatus.valueOf(restaurantRequestDTO.getStatus()));
            restaurant.setImageUrl(restaurantRequestDTO.getImageUrl());
            return restaurant;
        }

        /**
         * Create a new restaurant
         */
        @Override
        public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO restaurantRequestDTO) {

            log.info("Creating restaurant with name: {}", restaurantRequestDTO.getName());

            RestaurantValidator.validateCreate(restaurantRequestDTO);

            Restaurant restaurant = mapToEntity(restaurantRequestDTO);

            User owner = userRepository.findById(restaurantRequestDTO.getOwnerId())
                    .orElseThrow(() -> {
                        log.error("Owner not found with id: {}", restaurantRequestDTO.getOwnerId());
                        return new ResourceNotFoundException("User not found");
                    });

            restaurant.setOwner(owner);

            Restaurant saved = restaurantRepository.save(restaurant);

            log.info("Restaurant created successfully with id: {}", saved.getId());

            return restaurantResponseDTO(saved);
        }

        /**
         * Fetch all restaurants
         */
        @Override
        public List<RestaurantResponseDTO> getAllRestaurants() {
            log.info("Fetching all restaurants");

            return restaurantRepository.findAll()
                    .stream()
                    .map(this::restaurantResponseDTO)
                    .collect(Collectors.toList());
        }

        /**
         * Get restaurant by ID
         */
        @Override
        public RestaurantResponseDTO getRestaurantById(Long id) {

            log.info("Fetching restaurant with id: {}", id);

            Restaurant restaurant = restaurantRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Restaurant not found with id: {}", id);
                        return new ResourceNotFoundException("Restaurant not found");
                    });

            return restaurantResponseDTO(restaurant);
        }

        /**
         * Update restaurant details
         */
        @Override
        public RestaurantResponseDTO updateRestaurant(Long id, RestaurantRequestDTO restaurantRequestDTO) {

            log.info("Updating restaurant with id: {}", id);

            RestaurantValidator.validateUpdate(restaurantRequestDTO);

            Restaurant existing = restaurantRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Restaurant not found with id: {}", id);
                        return new ResourceNotFoundException("Restaurant not found");
                    });

            existing.setName(restaurantRequestDTO.getName());
            existing.setStatus(RestaurantStatus.valueOf(restaurantRequestDTO.getStatus()));

            if (Objects.nonNull(restaurantRequestDTO.getOwnerId())) {
                User owner = userRepository.findById(restaurantRequestDTO.getOwnerId())
                        .orElseThrow(() -> {
                            log.error("Owner not found with id: {}", restaurantRequestDTO.getOwnerId());
                            return new ResourceNotFoundException("User not found");
                        });
                existing.setOwner(owner);
            }

            existing.setImageUrl(restaurantRequestDTO.getImageUrl());

            Restaurant updated = restaurantRepository.save(existing);

            log.info("Restaurant updated successfully with id: {}", updated.getId());

            return restaurantResponseDTO(updated);
        }

        /**
         * Delete restaurant
         */
        @Override
        public void deleteRestaurant(Long id) {

            log.info("Deleting restaurant with id: {}", id);

            Restaurant restaurant = restaurantRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Restaurant not found with id: {}", id);
                        return new ResourceNotFoundException("Restaurant not found");
                    });

            restaurantRepository.delete(restaurant);

            log.info("Restaurant deleted successfully with id: {}", id);
        }

    /**
     * To get restaurants of logged in owner
     */
    @Override
    public List<RestaurantResponseDTO> getRestaurantsForLoggedInOwner() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        log.info("Fetching restaurants for owner: {}", email);

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Owner not found with email: {}", email);
                    return new ResourceNotFoundException("User not found");
                });

        List<Restaurant> restaurants = restaurantRepository.findByOwnerId(owner.getId());

        if (restaurants.isEmpty()) {
            log.warn("No restaurants found for ownerId: {}", owner.getId());
            return List.of(); // better than throwing exception
        }

        return restaurants.stream()
                .map(this::restaurantResponseDTO)
                .collect(Collectors.toList());
    }
}
