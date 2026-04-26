package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.constants.AppConstants;
import com.example.restaurant_order_portal.dto.AuthResponse;
import com.example.restaurant_order_portal.dto.UserLoginRequest;
import com.example.restaurant_order_portal.dto.UserRegisterRequest;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.enums.Role;
import com.example.restaurant_order_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for User-related APIs.
 *
 * It handles user registration and login requests.
 */
@RestController
@RequestMapping(AppConstants.BASE_USER_URL)
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
    @PostMapping(AppConstants.REGISTER_URL)
    public User registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {

        /**
         * DTO to Entity conversion
          */
        User user = new User();
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(userRegisterRequest.getPassword());
        user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        user.setRole(Role.valueOf(userRegisterRequest.getRole())); // String to Enum

        return userService.registerUser(user);
    }

    /**
     * API to login user.
     *
     * Endpoint: POST /api/users/login
     * @param userLoginRequest contains email and password for authentication
     * @return AuthResponse containing JWT token, email, and role
     */
    @PostMapping(AppConstants.LOGIN_URL)
    public AuthResponse loginUser(@RequestBody UserLoginRequest userLoginRequest) {

        return userService.loginUser(
                userLoginRequest.getEmail(),
                userLoginRequest.getPassword()
        );
    }
}
