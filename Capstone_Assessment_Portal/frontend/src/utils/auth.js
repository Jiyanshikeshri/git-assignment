/**
 * Utility functions for managing authentication data in the browser's Local Storage
 */

/**
 * Local Storage Keys
 */
const ACCESS_TOKEN_KEY = "access_token";
const REFRESH_TOKEN_KEY = "refresh_token";

/**
 * Save authentication tokens
 *
 * @param {string} accessToken - JWT access token
 * @param {string} refreshToken - JWT refresh token
 */
export const saveTokens = (accessToken, refreshToken) => {

    localStorage.setItem(
        ACCESS_TOKEN_KEY,
        accessToken
    );

    localStorage.setItem(
        REFRESH_TOKEN_KEY,
        refreshToken
    );
};

/**
 * Returns stored access token
 */
export const getAccessToken = () => {

    return localStorage.getItem(
        ACCESS_TOKEN_KEY
    );
};

/**
 * Returns stored refresh token
 */
export const getRefreshToken = () => {

    return localStorage.getItem(
        REFRESH_TOKEN_KEY
    );
};

/**
 * Removes authentication tokens
 */
export const removeTokens = () => {

    localStorage.removeItem(
        ACCESS_TOKEN_KEY
    );

    localStorage.removeItem(
        REFRESH_TOKEN_KEY
    );
};