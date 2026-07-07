/**
 * Authentication Service :- Handles all Authentication related API requests
 *
 * API methods will be implemented after Axios configuration
 */

import api from "./api";

/**
 * Register a new student
 */
export const registerUser = async (userData) => {

    const response = await api.post(
        "/auth/register",
        userData
    );

    return response.data;
};

/**
 * Login an existing user
 */
export const loginUser = async (credentials) => {

    const response = await api.post(
        "/auth/login",
        credentials
    );

    return response.data;
};

/**
 * Generate a new Access Token using a valid Refresh Token
 */
export const refreshAccessToken = async (refreshToken) => {

    const response = await api.post(
        "/auth/refresh",
        {
            refresh_token: refreshToken,
        }
    );

    return response.data;
};