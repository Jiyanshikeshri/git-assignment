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

/**
 * Attach JWT Access Token to every authenticated request.
 */
api.interceptors.request.use(

    (config) => {
        const token = localStorage.getItem(
            "access_token"
        );
        if (token) {
            config.headers.Authorization =
                `Bearer ${token}`;

        }
        return config;
    },

    (error) => Promise.reject(error),

);

export default api;