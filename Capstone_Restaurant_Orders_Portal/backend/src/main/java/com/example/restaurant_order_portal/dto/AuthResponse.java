package com.example.restaurant_order_portal.dto;

/**
 * DTO for authentication response.
 *
 * This class is used to send JWT token and user details after login.
 */
public class AuthResponse {

    private String token;
    private String email;
    private String role;

    public AuthResponse(){

    }

    public AuthResponse(String token, String email, String role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
