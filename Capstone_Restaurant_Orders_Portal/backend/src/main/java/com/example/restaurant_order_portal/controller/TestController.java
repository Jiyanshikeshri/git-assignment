package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.constants.AppConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for testing role-based APIs.
 */
@RestController
@RequestMapping(AppConstants.BASE_API)
public class TestController {

    /**
     * API accessible by USER role
     */
    @GetMapping(AppConstants.USER_TEST)
    public String userApi() {
        return "USER API is working!";
    }

    /**
     * API accessible by RESTAURANT_OWNER role
     */
    @GetMapping(AppConstants.ADMIN_TEST)
    public String adminApi() {
        return "ADMIN API is working!";
    }
}
