package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.entity.User;

/**
 * Service interface for User-related operations.
 *
 * Contains business logic methods for user registration and authentication.
 */
public interface UserService {

    User registerUser(User user);

    User loginUser(String email, String password);
}
