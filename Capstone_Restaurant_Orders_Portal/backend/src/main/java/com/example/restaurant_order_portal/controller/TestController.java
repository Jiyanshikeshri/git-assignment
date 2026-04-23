package com.example.restaurant_order_portal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/user/test")
    public String userApi() {
        return "USER API is working!";
    }

    @GetMapping("/admin/test")
    public String adminApi() {
        return "ADMIN API is working!";
    }
}
