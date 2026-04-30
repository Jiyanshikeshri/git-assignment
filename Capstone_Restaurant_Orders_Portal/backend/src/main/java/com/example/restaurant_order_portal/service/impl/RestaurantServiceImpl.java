package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.RestaurantRequestDTO;
import com.example.restaurant_order_portal.dto.RestaurantResponseDTO;
import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.enums.RestaurantStatus;
import com.example.restaurant_order_portal.repository.RestaurantRepository;
import com.example.restaurant_order_portal.repository.UserRepository;
import com.example.restaurant_order_portal.service.RestaurantService;
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
        private RestaurantResponseDTO mapToDTO(Restaurant restaurant) {
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

            Restaurant restaurant = mapToEntity(restaurantRequestDTO);

            User owner = userRepository.findById(restaurantRequestDTO.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            restaurant.setOwner(owner);

            Restaurant saved = restaurantRepository.save(restaurant);

            return mapToDTO(saved);
        }

        /**
         * Fetch all restaurants
         */
        @Override
        public List<RestaurantResponseDTO> getAllRestaurants() {
            return restaurantRepository.findAll()
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }

        /**
         * Get restaurant by ID
         */
        @Override
        public RestaurantResponseDTO getRestaurantById(Long id) {

            Restaurant restaurant = restaurantRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Restaurant not found"));

            return mapToDTO(restaurant);
        }

        /**
         * Update restaurant details
         */
        @Override
        public RestaurantResponseDTO updateRestaurant(Long id, RestaurantRequestDTO restaurantRequestDTO) {

            Restaurant existing = restaurantRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Restaurant not found"));

            existing.setName(restaurantRequestDTO.getName());
            existing.setStatus(RestaurantStatus.valueOf(restaurantRequestDTO.getStatus()));

            if (Objects.nonNull(restaurantRequestDTO.getOwnerId())) {
                User owner = userRepository.findById(restaurantRequestDTO.getOwnerId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                existing.setOwner(owner);
            }

            existing.setImageUrl(restaurantRequestDTO.getImageUrl());

            Restaurant updated = restaurantRepository.save(existing);

            return mapToDTO(updated);
        }

        /**
         * Delete restaurant
         */
        @Override
        public void deleteRestaurant(Long id) {

            Restaurant restaurant = restaurantRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Restaurant not found"));

            restaurantRepository.delete(restaurant);
        }

}
