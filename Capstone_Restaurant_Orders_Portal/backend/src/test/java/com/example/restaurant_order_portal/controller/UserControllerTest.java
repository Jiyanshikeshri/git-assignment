package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.dto.AuthResponse;
import com.example.restaurant_order_portal.dto.UserLoginRequest;
import com.example.restaurant_order_portal.dto.UserRegisterRequest;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.enums.Role;
import com.example.restaurant_order_portal.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserController.
 */
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    /**
     * Mocked service
     */
    @Mock
    private UserService userService;

    /**
     * Controller with injected mocks
     */
    @InjectMocks
    private UserController userController;

    /**
     * Register user successfully
     */
    @Test
    void shouldRegisterUserSuccessfully() {

        UserRegisterRequest request = new UserRegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        request.setPassword("password");
        request.setPhoneNumber("9999999999");
        request.setRole("USER");

        User savedUser = new User();
        savedUser.setFirstName("John");
        savedUser.setEmail("john@example.com");
        savedUser.setRole(Role.USER);

        when(userService.registerUser(any(User.class))).thenReturn(savedUser);

        User result = userController.registerUser(request);

        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
        assertEquals(Role.USER, result.getRole());

        verify(userService).registerUser(any(User.class));
    }

    /**
     * Login user successfully
     */
    @Test
    void shouldLoginUserSuccessfully() {

        UserLoginRequest request = new UserLoginRequest();
        request.setEmail("john@example.com");
        request.setPassword("password");

        AuthResponse response = new AuthResponse();
        // set token if needed: response.setToken("jwt-token");

        when(userService.loginUser("john@example.com", "password"))
                .thenReturn(response);

        AuthResponse result = userController.loginUser(request);

        assertNotNull(result);

        verify(userService).loginUser("john@example.com", "password");
    }

    /**
     * Get user by ID
     */
    @Test
    void shouldGetUserById() {

        Long id = 1L;

        User user = new User();
        user.setEmail("john@example.com");

        when(userService.getUserById(id)).thenReturn(user);

        User result = userController.getUserById(id);

        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());

        verify(userService).getUserById(id);
    }

    /**
     * Exception when user not found
     */
    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        Long id = 1L;

        when(userService.getUserById(id))
                .thenThrow(new RuntimeException("User not found"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> userController.getUserById(id)
        );

        assertEquals("User not found", ex.getMessage());
    }
}