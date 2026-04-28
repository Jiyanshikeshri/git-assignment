package com.example.restaurant_order_portal.dto;

/**
 * DTO for authentication response.
 *
 * This class is used to send JWT token and user details after login.
 */
public class AuthResponse {

    /**
     * JWT token generated after successful authentication.
     */
    private String token;

    /**
     * Email of the authenticated user.
     */
    private String email;

    /**
     * Role of the authenticated user (e.g., USER, RESTAURANT_OWNER).
     */
    private String role;

    /**
     * User ID of logged in user
     */
    private Long userId;

    public AuthResponse(){

    }

    public AuthResponse(String token, String email, String role, Long userId) {
        this.token = token;
        this.email = email;
        this.role = role;
        this.userId = userId;
    }

    /**
     * Gets the JWT token.
     *
     * @return JWT token string
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the email of the user.
     *
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the role of the user.
     *
     * @return user role
     */
    public String getRole() {
        return role;
    }

    /**
     * Gets the user id of logged in user
     * @return
     */
    public Long getUserId() {
        return userId;
    }
}
