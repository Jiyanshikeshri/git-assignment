package com.example.session3.service;

import com.example.session3.model.User;
import com.example.session3.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.session3.model.SubmitRequest;

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

    public boolean validateUser(SubmitRequest request) {

    // basic null or empty checks
    if (request.getName() == null || request.getName().isEmpty()) {
        return false;
    }

    if (request.getAge() == null) {
        return false;
    }

    if (request.getRole() == null || request.getRole().isEmpty()) {
        return false;
    }

    return true;
    }


    // delete with confirmation check
    public String deleteUser(int id, Boolean confirm) {

    // check confirmation
    if (confirm == null || !confirm) {
        return "Confirmation required";
    }

    User user = userRepository.getUserById(id);

    if (user == null) {
        return "User not found";
    }

    userRepository.deleteUser(user);

    return "User deleted successfully";
    }
}