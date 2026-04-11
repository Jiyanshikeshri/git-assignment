package com.example.session2.service;

import com.example.session2.model.User;
import com.example.session2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor Injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public void createUser(User user) {
        userRepository.addUser(user);
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }
}