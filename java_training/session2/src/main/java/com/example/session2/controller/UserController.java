package com.example.session2.controller;

import com.example.session2.model.User;
import com.example.session2.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // POST create user
    @PostMapping
    public String createUser(@RequestBody User user) {
        userService.createUser(user);
        return "User created successfully";
    }

    // GET user by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }
}