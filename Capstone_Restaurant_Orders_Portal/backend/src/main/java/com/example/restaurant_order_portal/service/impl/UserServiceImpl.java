package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.AuthResponse;
import com.example.restaurant_order_portal.entity.Cart;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.exception.BadRequestException;
import com.example.restaurant_order_portal.exception.ConflictException;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.CartRepository;
import com.example.restaurant_order_portal.repository.UserRepository;
import com.example.restaurant_order_portal.security.JwtUtil;
import com.example.restaurant_order_portal.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation class for UserService.
 *
 * Contains business logic for user registration and login.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor-based dependency injection.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, CartRepository cartRepository, JwtUtil jwtUtil,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
    }

    /**
     * Registers a new user.
     * checks if email already exists. if yes, throws exception else saves the user
     */
    @Override
    public User registerUser(User user) {
        log.info("Registering user with email: {}", user.getEmail());

        if (user.getEmail() == null || user.getPassword() == null) {
            log.error("Email or password is null");
            throw new BadRequestException("Email and password are required");
        }

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            log.error("User already exists with email: {}", user.getEmail());
            throw new ConflictException("User already exists with this email");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        log.info("User registered successfully with id: {}", savedUser.getId());

        return savedUser;
    }

    /**
     * Authenticates user login.
     * Finds user by email, if not found throws exception else matches password and if mismatch then throws exception
     */
    @Override
    public AuthResponse loginUser(String email, String password) {

        log.info("User login attempt for email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new ResourceNotFoundException("User not found");
                });

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.error("Invalid password attempt for email: {}", email);
            throw new BadRequestException("Invalid password");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        log.info("User logged in successfully: {}", email);

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name(),
                user.getId()
        );
    }

    @Override
    public User getUserById(Long id) {

        log.info("Fetching user with id: {}", id);

        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new ResourceNotFoundException("User not found");
                });
    }
}
