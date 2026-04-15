package com.example.session3.repository;

import com.example.session3.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// Repository to store users in memory
@Repository
public class UserRepository {

    private List<User> users = new ArrayList<>();

    public UserRepository() {
        // Adding dummy users initially
        users.add(new User(1, "Priya", 25, "USER"));
        users.add(new User(2, "Rahul", 30, "ADMIN"));
        users.add(new User(3, "Amit", 30, "USER"));
        users.add(new User(4, "Neha", 28, "USER"));
        users.add(new User(5, "Rohit", 35, "ADMIN"));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void deleteUser(User user) {
        users.remove(user);
    }
}