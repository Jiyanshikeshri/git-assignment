/**
 * Authentication Service :- Handles all Authentication related API requests
 *
 * API methods will be implemented after Axios configuration
 */

import api from "./api";

import { getPublicKey } from "./publicKeyService";
import { encryptPassword } from "../utils/encryption";

/**
 * Register a new student
 */
export const registerUser = async (userData) => {

    const publicKey = await getPublicKey();

    const encryptedPassword = encryptPassword(
        userData.password,
        publicKey
    );

    const response = await api.post(
        "/auth/register",
        {
            ...userData,
            password: encryptedPassword,
        }
    );

    return response.data;
};

/**
 * Login an existing user
 */
export const loginUser = async (credentials) => {

    const publicKey = await getPublicKey();

    const encryptedPassword = encryptPassword(
        credentials.password,
        publicKey
    );

    const response = await api.post(
        "/auth/login",
        {
            ...credentials,
            password: encryptedPassword,
        }
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