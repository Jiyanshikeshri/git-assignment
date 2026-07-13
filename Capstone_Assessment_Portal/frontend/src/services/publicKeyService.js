/**
 * Service for fetching the RSA public key from the backend
 */

import api from "./api";
import { API_ENDPOINTS } from "../constants/apiEndpoints";

/**
 * Fetch RSA public key
 */
export const getPublicKey = async () => {

    const response = await api.get(
        API_ENDPOINTS.AUTH.PUBLIC_KEY
    );

    return response.data.public_key;
};