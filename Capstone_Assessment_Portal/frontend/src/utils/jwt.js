/**
 * Utility functions for decoding JWT tokens
 */

import { jwtDecode } from "jwt-decode";

/**
 * Decodes an access token and returns the payload
 */
export const decodeToken = (token) => {

    try {
        return jwtDecode(token);
    }
    catch (error) {

        console.error(
            "Invalid JWT Token:",
            error
        );

        return null;
    }
};