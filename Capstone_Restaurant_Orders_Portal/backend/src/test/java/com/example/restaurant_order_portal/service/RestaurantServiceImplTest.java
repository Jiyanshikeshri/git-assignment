package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.RestaurantRequestDTO;
import com.example.restaurant_order_portal.dto.RestaurantResponseDTO;
import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.enums.RestaurantStatus;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.RestaurantRepository;
import com.example.restaurant_order_portal.repository.UserRepository;
import com.example.restaurant_order_portal.service.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private User user;
    private Restaurant restaurant;
    private RestaurantRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);
        user.setFirstName("John");

        restaurant = new Restaurant();
        ReflectionTestUtils.setField(restaurant, "id", 10L);
        restaurant.setName("Pizza Hut");
        restaurant.setStatus(RestaurantStatus.OPEN);
        restaurant.setOwner(user);
        restaurant.setImageUrl("img.png");

        requestDTO = new RestaurantRequestDTO();
        requestDTO.setName("Pizza Hut");
        requestDTO.setStatus("OPEN");
        requestDTO.setOwnerId(1L);
        requestDTO.setImageUrl("img.png");
    }

    /**
     * Create Restaurant
     */

    @Test
    void createRestaurant_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        RestaurantResponseDTO response = restaurantService.createRestaurant(requestDTO);

        assertNotNull(response);
        assertEquals("Pizza Hut", response.getName());
    }

    @Test
    void createRestaurant_ownerNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                restaurantService.createRestaurant(requestDTO));
    }

    /**
     * Get all restaurants
     */

    @Test
    void getAllRestaurants_success() {
        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));

        List<RestaurantResponseDTO> result = restaurantService.getAllRestaurants();

        assertEquals(1, result.size());
    }

    @Test
    void getAllRestaurants_empty() {
        when(restaurantRepository.findAll()).thenReturn(List.of());

        List<RestaurantResponseDTO> result = restaurantService.getAllRestaurants();

        assertTrue(result.isEmpty());
    }

    /**
     * Get restaurants by id
     */

    @Test
    void getRestaurantById_success() {
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));

        RestaurantResponseDTO response = restaurantService.getRestaurantById(10L);

        assertEquals("Pizza Hut", response.getName());
    }

    @Test
    void getRestaurantById_notFound() {
        when(restaurantRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                restaurantService.getRestaurantById(10L));
    }

    /**
     * Update restaurant
     */

    @Test
    void updateRestaurant_success() {
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        RestaurantResponseDTO response =
                restaurantService.updateRestaurant(10L, requestDTO);

        assertEquals("Pizza Hut", response.getName());
    }

    @Test
    void updateRestaurant_ownerUpdated() {
        User newOwner = new User();
        ReflectionTestUtils.setField(newOwner, "id", 2L);

        requestDTO.setOwnerId(2L);

        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(2L)).thenReturn(Optional.of(newOwner));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        RestaurantResponseDTO response =
                restaurantService.updateRestaurant(10L, requestDTO);

        assertNotNull(response);
    }

    @Test
    void updateRestaurant_notFound() {
        when(restaurantRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                restaurantService.updateRestaurant(10L, requestDTO));
    }

    @Test
    void updateRestaurant_ownerNotFound() {
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                restaurantService.updateRestaurant(10L, requestDTO));
    }

    /**
     * Delete restaurants
     */

    @Test
    void deleteRestaurant_success() {
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));

        restaurantService.deleteRestaurant(10L);

        verify(restaurantRepository, times(1)).delete(restaurant);
    }

    @Test
    void deleteRestaurant_notFound() {
        when(restaurantRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                restaurantService.deleteRestaurant(10L));
    }
}