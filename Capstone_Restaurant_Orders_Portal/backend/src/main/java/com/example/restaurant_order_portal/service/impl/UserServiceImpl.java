package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.AuthResponse;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.repository.UserRepository;
import com.example.restaurant_order_portal.security.JwtUtil;
import com.example.restaurant_order_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation class for UserService.
 *
 * Contains business logic for user registration and login.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    /**
     * Constructor-based dependency injection.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Registers a new user.
     * checks if email already exists. if yes, throws exception else saves the user
     */
    @Override
    public User registerUser(User user) {

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists with this email");
        }

        return userRepository.save(user);
    }

    /**
     * Authenticates user login.
     * Finds user by email, if not found throws exception else matches password and if mismatch then throws exception
     */
    @Override
    public AuthResponse loginUser(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        // It generates JWT
        String token = jwtUtil.generateToken(user.getEmail());

        // return DTO
        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name()
        );
    }
}
