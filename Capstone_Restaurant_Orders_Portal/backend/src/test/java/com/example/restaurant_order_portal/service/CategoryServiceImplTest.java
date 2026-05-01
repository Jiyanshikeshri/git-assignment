package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.CategoryRequestDTO;
import com.example.restaurant_order_portal.dto.CategoryResponseDTO;
import com.example.restaurant_order_portal.entity.Category;
import com.example.restaurant_order_portal.entity.Restaurant;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.CategoryRepository;
import com.example.restaurant_order_portal.repository.MenuItemRepository;
import com.example.restaurant_order_portal.repository.RestaurantRepository;
import com.example.restaurant_order_portal.service.impl.CategoryServiceImpl;
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

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Restaurant restaurant;
    private Category category;
    private CategoryRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restaurant = new Restaurant();
        ReflectionTestUtils.setField(restaurant, "id", 1L);
        restaurant.setName("Pizza Hut");

        category = new Category();
        ReflectionTestUtils.setField(category, "id", 10L);
        category.setName("Pizza");
        category.setImageUrl("img.png");
        category.setRestaurant(restaurant);

        requestDTO = new CategoryRequestDTO();
        requestDTO.setName("Pizza");
        requestDTO.setImageUrl("img.png");
        requestDTO.setRestaurantId(1L);
    }

    /**
     * Create category
     */

    @Test
    void createCategory_success() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO response = categoryService.createCategory(requestDTO);

        assertNotNull(response);
        assertEquals("Pizza", response.getName());
    }

    @Test
    void createCategory_restaurantNotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                categoryService.createCategory(requestDTO));
    }

    /**
     * Get categories
     */

    @Test
    void getCategoriesByRestaurant_success() {
        when(categoryRepository.findByRestaurantId(1L))
                .thenReturn(List.of(category));

        List<CategoryResponseDTO> result =
                categoryService.getCategoriesByRestaurant(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getCategoriesByRestaurant_empty() {
        when(categoryRepository.findByRestaurantId(1L))
                .thenReturn(List.of());

        List<CategoryResponseDTO> result =
                categoryService.getCategoriesByRestaurant(1L);

        assertTrue(result.isEmpty());
    }

    /**
     * Update category
     */

    @Test
    void updateCategory_success() {
        when(categoryRepository.findById(10L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO response =
                categoryService.updateCategory(10L, requestDTO);

        assertEquals("Pizza", response.getName());
    }

    @Test
    void updateCategory_notFound() {
        when(categoryRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                categoryService.updateCategory(10L, requestDTO));
    }

    /**
     * Delete category
     */

    @Test
    void deleteCategory_success() {
        when(categoryRepository.findById(10L)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(10L);

        verify(menuItemRepository, times(1)).deleteByCategoryId(10L);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void deleteCategory_notFound() {
        when(categoryRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                categoryService.deleteCategory(10L));
    }
}