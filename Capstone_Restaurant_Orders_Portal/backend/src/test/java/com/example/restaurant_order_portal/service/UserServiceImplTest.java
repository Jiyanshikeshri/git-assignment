package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.AuthResponse;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.enums.Role;
import com.example.restaurant_order_portal.exception.BadRequestException;
import com.example.restaurant_order_portal.exception.ConflictException;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.CartRepository;
import com.example.restaurant_order_portal.repository.UserRepository;
import com.example.restaurant_order_portal.security.JwtUtil;
import com.example.restaurant_order_portal.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private CartRepository cartRepository;
    @Mock private JwtUtil jwtUtil;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);
        user.setEmail("test@gmail.com");
        user.setPassword("plain123");
        user.setRole(Role.USER);
    }

    /**
     * Register
     */

    @Test
    void registerUser_success() {
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("plain123")).thenReturn("encoded123");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User result = userService.registerUser(user);

        assertNotNull(result);
        assertEquals("encoded123", result.getPassword());
    }

    @Test
    void registerUser_userAlreadyExists() {
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));

        assertThrows(ConflictException.class, () ->
                userService.registerUser(user));
    }

    @Test
    void registerUser_nullFields() {
        user.setEmail(null);

        assertThrows(BadRequestException.class, () ->
                userService.registerUser(user));
    }

    /**
     * Login
     */

    @Test
    void loginUser_success() {
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("password", "encodedPassword"))
                .thenReturn(true);

        when(jwtUtil.generateToken(anyString(), anyString()))
                .thenReturn("token123");

        AuthResponse response = userService.loginUser("test@gmail.com", "password");

        assertNotNull(response);
        assertEquals("token123", response.getToken());
    }

    @Test
    void loginUser_userNotFound() {
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                userService.loginUser("test@gmail.com", "plain123"));
    }

    @Test
    void loginUser_wrongPassword() {
        user.setPassword("encoded123");

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("plain123", "encoded123")).thenReturn(false);

        assertThrows(BadRequestException.class, () ->
                userService.loginUser("test@gmail.com", "plain123"));
    }
    
    /**
     * Get user by id
     */

    @Test
    void getUserById_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                userService.getUserById(1L));
    }
}