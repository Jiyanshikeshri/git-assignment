/**
 * Central Axios configuration for the Assessment Portal
 */

import axios from "axios";

/**
 * Axios instance used throughout the application.
 */
const api = axios.create({

    // Backend FastAPI Base URL
    baseURL: "http://127.0.0.1:8000",

    headers: {
        "Content-Type": "application/json",
    },

});

export default api;