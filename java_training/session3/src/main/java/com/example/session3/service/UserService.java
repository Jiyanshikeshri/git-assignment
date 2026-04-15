package com.example.session3.service;

import com.example.session3.model.User;
import com.example.session3.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    // constructor injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> searchUsers(String name, Integer age, String role) {

        List<User> allUsers = userRepository.getAllUsers();

        // if no parameters then return all users
        if (name == null && age == null && role == null) {
            return allUsers;
        }

        List<User> result = new ArrayList<>();

        for (User user : allUsers) {

            boolean matches = true;

            // check name in case of case-insensitive
            if (name != null && !user.getName().equalsIgnoreCase(name)) {
                matches = false;
            }

            // check age (exact match)
            if (age != null && user.getAge() != age) {
                matches = false;
            }

            // check role (case-insensitive)
            if (role != null && !user.getRole().equalsIgnoreCase(role)) {
                matches = false;
            }

            // if all conditions satisfied then add to result
            if (matches) {
                result.add(user);
            }
        }

        return result;
    }
}