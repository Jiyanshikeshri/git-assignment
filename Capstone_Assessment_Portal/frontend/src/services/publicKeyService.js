/**
 * Service for fetching the RSA public key from the backend
 */

import api from "./api";

/**
 * Fetch RSA public key
 */
export const getPublicKey = async () => {

    const response = await api.get(
        "/auth/public-key"
    );

    return response.data.public_key;
};