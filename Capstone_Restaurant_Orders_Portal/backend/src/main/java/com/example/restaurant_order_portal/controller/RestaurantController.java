package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.constants.AppConstants;
import com.example.restaurant_order_portal.dto.RestaurantRequestDTO;
import com.example.restaurant_order_portal.dto.RestaurantResponseDTO;
import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.service.RestaurantService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * REST Controller for Restaurant APIs.
 *
 * Handles CRUD operations for restaurants.
 */
@RestController
@RequestMapping(AppConstants.BASE_RESTAURANT_URL)
public class RestaurantController {

    private final RestaurantService restaurantService;

    /**
     * Constructor-based dependency injection
     */
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * Create restaurant
     */
    @PostMapping
    public RestaurantResponseDTO createRestaurant(@RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        System.out.println("Owner ID: " + restaurantRequestDTO.getOwnerId());
        return restaurantService.createRestaurant(restaurantRequestDTO);
    }

    /**
     * Get all restaurants
     */
    @GetMapping
    public List<RestaurantResponseDTO> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    /**
     * Get restaurant by ID
     */
    @GetMapping(AppConstants.RESTAURANT_ID)
    public RestaurantResponseDTO getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    /**
     * Update restaurant
     */
    @PutMapping(AppConstants.RESTAURANT_ID)
    public RestaurantResponseDTO updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantRequestDTO restaurantRequestDTO
    ) {
        return restaurantService.updateRestaurant(id, restaurantRequestDTO);
    }

    /**
     * Delete restaurant
     */
    @DeleteMapping(AppConstants.RESTAURANT_ID)
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }

}
