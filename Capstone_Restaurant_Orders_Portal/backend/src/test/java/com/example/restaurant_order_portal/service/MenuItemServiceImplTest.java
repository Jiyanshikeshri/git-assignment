package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.MenuItemRequestDTO;
import com.example.restaurant_order_portal.dto.MenuItemResponseDTO;
import com.example.restaurant_order_portal.entity.Category;
import com.example.restaurant_order_portal.entity.MenuItem;
import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.CategoryRepository;
import com.example.restaurant_order_portal.repository.MenuItemRepository;
import com.example.restaurant_order_portal.repository.RestaurantRepository;
import com.example.restaurant_order_portal.service.impl.MenuItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

public class MenuItemServiceImplTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private MenuItemServiceImpl menuItemService;

    private Category category;
    private Restaurant restaurant;
    private MenuItem menuItem;
    private MenuItemRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category();
        ReflectionTestUtils.setField(category, "id", 1L);
        category.setName("Pizza");

        restaurant = new Restaurant();
        ReflectionTestUtils.setField(restaurant, "id", 10L);
        restaurant.setName("Dominos");

        menuItem = new MenuItem();
        ReflectionTestUtils.setField(menuItem, "id", 100L);
        menuItem.setName("Farmhouse Pizza");
        menuItem.setPrice(250.0);
        menuItem.setCategory(category);
        menuItem.setRestaurant(restaurant);
        menuItem.setImageUrl("img.png");

        requestDTO = new MenuItemRequestDTO();
        requestDTO.setName("Farmhouse Pizza");
        requestDTO.setPrice(250.0);
        requestDTO.setCategoryId(1L);
        requestDTO.setRestaurantId(10L);
        requestDTO.setImageUrl("img.png");
    }

    /**
     * Create Menu Item
     */

    @Test
    void createMenuItem_success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItemResponseDTO response = menuItemService.createMenuItem(requestDTO);

        assertNotNull(response);
        assertEquals("Farmhouse Pizza", response.getName());
    }

    @Test
    void createMenuItem_categoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                menuItemService.createMenuItem(requestDTO));
    }

    @Test
    void createMenuItem_restaurantNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(restaurantRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                menuItemService.createMenuItem(requestDTO));
    }

    /**
     * Get menu items
     */

    @Test
    void getMenuItemsByRestaurant_success() {
        when(menuItemRepository.findByRestaurantId(10L))
                .thenReturn(List.of(menuItem));

        List<MenuItemResponseDTO> result =
                menuItemService.getMenuItemsByRestaurant(10L);

        assertEquals(1, result.size());
    }

    @Test
    void getMenuItemsByCategory_success() {
        when(menuItemRepository.findByCategoryId(1L))
                .thenReturn(List.of(menuItem));

        List<MenuItemResponseDTO> result =
                menuItemService.getMenuItemsByCategory(1L);

        assertEquals(1, result.size());
    }

    /**
     * Update menu items
     */

    @Test
    void updateMenuItem_success() {
        when(menuItemRepository.findById(100L)).thenReturn(Optional.of(menuItem));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItemResponseDTO response =
                menuItemService.updateMenuItem(100L, requestDTO);

        assertEquals("Farmhouse Pizza", response.getName());
    }

    @Test
    void updateMenuItem_notFound() {
        when(menuItemRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                menuItemService.updateMenuItem(100L, requestDTO));
    }

    /**
     * Delete menu items
     */

    @Test
    void deleteMenuItem_success() {
        when(menuItemRepository.existsById(100L)).thenReturn(true);

        menuItemService.deleteMenuItem(100L);

        verify(menuItemRepository, times(1)).deleteById(100L);
    }

    @Test
    void deleteMenuItem_notFound() {
        when(menuItemRepository.existsById(100L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () ->
                menuItemService.deleteMenuItem(100L));
    }
}