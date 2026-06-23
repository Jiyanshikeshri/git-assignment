package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.dto.CategoryRequestDTO;
import com.example.restaurant_order_portal.dto.CategoryResponseDTO;
import com.example.restaurant_order_portal.service.CategoryService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CategoryController.
 */
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    /**
     * Mocked CategoryService.
     */
    @Mock
    private CategoryService categoryService;

    /**
     * Inject mocks into controller.
     */
    @InjectMocks
    private CategoryController categoryController;

    /**
     * Tests successful category creation.
     */
    @Test
    void shouldCreateCategorySuccessfully() {

        CategoryRequestDTO request = new CategoryRequestDTO();

        CategoryResponseDTO response =
                new CategoryResponseDTO(1L, "Pizza", 10L, "Dominos", "img.png");

        when(categoryService.createCategory(request)).thenReturn(response);

        CategoryResponseDTO result = categoryController.createCategory(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(categoryService).createCategory(request);
    }

    /**
     * Tests fetching categories by restaurant ID.
     */
    @Test
    void shouldGetCategoriesByRestaurant() {

        Long restaurantId = 1L;

        CategoryResponseDTO response =
                new CategoryResponseDTO(1L, "Pizza", 10L, "Dominos", "img.png");

        when(categoryService.getCategoriesByRestaurant(restaurantId))
                .thenReturn(List.of(response));

        List<CategoryResponseDTO> result =
                categoryController.getCategoriesByRestaurant(restaurantId);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());

        verify(categoryService).getCategoriesByRestaurant(restaurantId);
    }

    /**
     * Tests updating a category successfully.
     */
    @Test
    void shouldUpdateCategorySuccessfully() {

        Long categoryId = 1L;

        CategoryRequestDTO request = new CategoryRequestDTO();

        CategoryResponseDTO response =
                new CategoryResponseDTO(1L, "Pizza", 10L, "Dominos", "img.png");

        when(categoryService.updateCategory(categoryId, request))
                .thenReturn(response);

        CategoryResponseDTO result =
                categoryController.updateCategory(categoryId, request);

        assertNotNull(result);
        assertEquals(categoryId, result.getId());

        verify(categoryService).updateCategory(categoryId, request);
    }

    /**
     * Tests deleting a category successfully.
     */
    @Test
    void shouldDeleteCategorySuccessfully() {

        Long categoryId = 1L;

        doNothing().when(categoryService).deleteCategory(categoryId);

        String result = categoryController.deleteCategory(categoryId);

        assertEquals("Category deleted successfully", result);

        verify(categoryService).deleteCategory(categoryId);
    }

    /**
     * Tests exception when category not found during update.
     */
    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {

        Long categoryId = 1L;
        CategoryRequestDTO request = new CategoryRequestDTO();

        when(categoryService.updateCategory(categoryId, request))
                .thenThrow(new RuntimeException("Category not found"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> categoryController.updateCategory(categoryId, request)
        );

        assertEquals("Category not found", ex.getMessage());
    }
}