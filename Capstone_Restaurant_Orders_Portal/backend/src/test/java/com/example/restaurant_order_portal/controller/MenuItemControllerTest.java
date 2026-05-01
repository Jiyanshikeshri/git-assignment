package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.dto.MenuItemRequestDTO;
import com.example.restaurant_order_portal.dto.MenuItemResponseDTO;
import com.example.restaurant_order_portal.service.MenuItemService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for MenuItemController.
 */
@ExtendWith(MockitoExtension.class)
public class MenuItemControllerTest {

    /**
     * Mocked service layer
     */
    @Mock
    private MenuItemService menuItemService;

    /**
     * Controller with injected mocks
     */
    @InjectMocks
    private MenuItemController menuItemController;

    /**
     * Create menu item successfully
     */
    @Test
    void shouldCreateMenuItemSuccessfully() {

        MenuItemRequestDTO request = new MenuItemRequestDTO();

        MenuItemResponseDTO response =
                new MenuItemResponseDTO(
                        1L,
                        "Pizza",
                        200.0,
                        "Fast Food",
                        "Dominos",
                        "img.png"
                );

        when(menuItemService.createMenuItem(request)).thenReturn(response);

        MenuItemResponseDTO result = menuItemController.createMenuItem(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Pizza", result.getName());

        verify(menuItemService).createMenuItem(request);
    }

    /**
     * Get menu items by restaurant
     */
    @Test
    void shouldGetMenuItemsByRestaurant() {

        Long restaurantId = 10L;

        MenuItemResponseDTO response =
                new MenuItemResponseDTO(
                        1L,
                        "Pizza",
                        200.0,
                        "Fast Food",
                        "Dominos",
                        "img.png"
                );

        when(menuItemService.getMenuItemsByRestaurant(restaurantId))
                .thenReturn(List.of(response));

        List<MenuItemResponseDTO> result =
                menuItemController.getByRestaurant(restaurantId);

        assertEquals(1, result.size());
        assertEquals("Dominos", result.get(0).getRestaurantName());

        verify(menuItemService).getMenuItemsByRestaurant(restaurantId);
    }

    /**
     * Get menu items by category
     */
    @Test
    void shouldGetMenuItemsByCategory() {

        Long categoryId = 5L;

        MenuItemResponseDTO response =
                new MenuItemResponseDTO(
                        1L,
                        "Burger",
                        150.0,
                        "Snacks",
                        "KFC",
                        "img.png"
                );

        when(menuItemService.getMenuItemsByCategory(categoryId))
                .thenReturn(List.of(response));

        List<MenuItemResponseDTO> result =
                menuItemController.getByCategory(categoryId);

        assertEquals(1, result.size());
        assertEquals("Snacks", result.get(0).getCategoryName());

        verify(menuItemService).getMenuItemsByCategory(categoryId);
    }

    /**
     * Update menu item successfully
     */
    @Test
    void shouldUpdateMenuItemSuccessfully() {

        Long id = 1L;

        MenuItemRequestDTO request = new MenuItemRequestDTO();

        MenuItemResponseDTO response =
                new MenuItemResponseDTO(
                        id,
                        "Updated Pizza",
                        250.0,
                        "Fast Food",
                        "Dominos",
                        "img.png"
                );

        when(menuItemService.updateMenuItem(id, request))
                .thenReturn(response);

        MenuItemResponseDTO result =
                menuItemController.updateMenuItem(id, request);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated Pizza", result.getName());

        verify(menuItemService).updateMenuItem(id, request);
    }

    /**
     * Delete menu item successfully
     */
    @Test
    void shouldDeleteMenuItemSuccessfully() {

        Long id = 1L;

        doNothing().when(menuItemService).deleteMenuItem(id);

        String result = menuItemController.deleteMenuItem(id);

        assertEquals("Menu Item deleted successfully", result);

        verify(menuItemService).deleteMenuItem(id);
    }

    /**
     * Exception when menu item not found
     */
    @Test
    void shouldThrowExceptionWhenMenuItemNotFound() {

        Long id = 1L;

        when(menuItemService.updateMenuItem(eq(id), any()))
                .thenThrow(new RuntimeException("Menu item not found"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> menuItemController.updateMenuItem(id, new MenuItemRequestDTO())
        );

        assertEquals("Menu item not found", ex.getMessage());
    }
}