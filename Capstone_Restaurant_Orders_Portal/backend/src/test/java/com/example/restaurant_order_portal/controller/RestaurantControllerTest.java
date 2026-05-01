package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.dto.RestaurantRequestDTO;
import com.example.restaurant_order_portal.dto.RestaurantResponseDTO;
import com.example.restaurant_order_portal.service.RestaurantService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for RestaurantController.
 */
@ExtendWith(MockitoExtension.class)
public class RestaurantControllerTest {

    /**
     * Mocked service
     */
    @Mock
    private RestaurantService restaurantService;

    /**
     * Controller with injected mocks
     */
    @InjectMocks
    private RestaurantController restaurantController;

    /**
     * Create restaurant successfully
     */
    @Test
    void shouldCreateRestaurantSuccessfully() {

        RestaurantRequestDTO request = new RestaurantRequestDTO();

        RestaurantResponseDTO response = new RestaurantResponseDTO();

        when(restaurantService.createRestaurant(request)).thenReturn(response);

        RestaurantResponseDTO result =
                restaurantController.createRestaurant(request);

        assertNotNull(result);

        verify(restaurantService).createRestaurant(request);
    }

    /**
     * Get all restaurants
     */
    @Test
    void shouldGetAllRestaurants() {

        RestaurantResponseDTO response = new RestaurantResponseDTO();

        when(restaurantService.getAllRestaurants())
                .thenReturn(List.of(response));

        List<RestaurantResponseDTO> result =
                restaurantController.getAllRestaurants();

        assertEquals(1, result.size());

        verify(restaurantService).getAllRestaurants();
    }

    /**
     * Get restaurant by ID
     */
    @Test
    void shouldGetRestaurantById() {

        Long id = 1L;

        RestaurantResponseDTO response = new RestaurantResponseDTO();

        when(restaurantService.getRestaurantById(id))
                .thenReturn(response);

        RestaurantResponseDTO result =
                restaurantController.getRestaurantById(id);

        assertNotNull(result);

        verify(restaurantService).getRestaurantById(id);
    }

    /**
     * Update restaurant successfully
     */
    @Test
    void shouldUpdateRestaurantSuccessfully() {

        Long id = 1L;

        RestaurantRequestDTO request = new RestaurantRequestDTO();

        RestaurantResponseDTO response = new RestaurantResponseDTO();

        when(restaurantService.updateRestaurant(id, request))
                .thenReturn(response);

        RestaurantResponseDTO result =
                restaurantController.updateRestaurant(id, request);

        assertNotNull(result);

        verify(restaurantService).updateRestaurant(id, request);
    }

    /**
     * Delete restaurant successfully
     */
    @Test
    void shouldDeleteRestaurantSuccessfully() {

        Long id = 1L;

        doNothing().when(restaurantService).deleteRestaurant(id);

        assertDoesNotThrow(() ->
                restaurantController.deleteRestaurant(id)
        );

        verify(restaurantService).deleteRestaurant(id);
    }

    /**
     * Exception when restaurant not found
     */
    @Test
    void shouldThrowExceptionWhenRestaurantNotFound() {

        Long id = 1L;

        when(restaurantService.getRestaurantById(id))
                .thenThrow(new RuntimeException("Restaurant not found"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> restaurantController.getRestaurantById(id)
        );

        assertEquals("Restaurant not found", ex.getMessage());
    }
}