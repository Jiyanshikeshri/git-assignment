package com.example.session3.controller;

import com.example.session3.model.User;
import com.example.session3.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.example.session3.model.SubmitRequest;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // API to search users based on query parameters
    @GetMapping("/search")
    public List<User> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String role
    ) {

        // passing parameters to the service layer
        return userService.searchUsers(name, age, role);
    }

    //API to submit valid data
    @PostMapping("/submit")
    public String submitData(@RequestBody SubmitRequest request) {

    boolean isValid = userService.validateUser(request);

    if (!isValid) {
        return "Invalid input"; 
    }

    return "Data submitted successfully";
    }

    // Delete request by id
    @DeleteMapping("/{id}")
    public String deleteUser(
        @PathVariable int id,
        @RequestParam(required = false) Boolean confirm
    ) {
        return userService.deleteUser(id, confirm);
    }
}