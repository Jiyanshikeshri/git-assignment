package com.example.restaurant_order_portal.dto;

/**
 * DTO for user login request.
 *
 * This class is used to receive login credentials.
 */
public class UserLoginRequest {

    private String email;
    private String password;

    public UserLoginRequest() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
