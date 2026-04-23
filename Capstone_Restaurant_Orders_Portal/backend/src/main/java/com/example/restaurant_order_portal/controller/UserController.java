package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.dto.AuthResponse;
import com.example.restaurant_order_portal.dto.UserLoginRequest;
import com.example.restaurant_order_portal.dto.UserRegisterRequest;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.enums.Role;
import com.example.restaurant_order_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for User-related APIs.
 *
 * It handles user registration and login requests.
 */

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructor-based dependency injection.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * API to register a new user.
     *
     * Endpoint: POST /api/users/register
     */
    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegisterRequest request) {

        // DTO to Entity conversion
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(Role.valueOf(request.getRole())); // String to Enum

        return userService.registerUser(user);
    }

    /**
     * API to login user.
     *
     * Endpoint: POST /api/users/login
     * @param request contains email and password for authentication
     * @return AuthResponse containing JWT token, email, and role
     */
    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody UserLoginRequest request) {

        return userService.loginUser(
                request.getEmail(),
                request.getPassword()
        );
    }
}
