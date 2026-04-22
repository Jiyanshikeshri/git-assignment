package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

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
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    /**
     * API to login user.
     *
     * Endpoint: POST /api/users/login
     */
    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                          @RequestParam String password) {
        return userService.loginUser(email, password);
    }
}
