/**
 * Category Service
 * Handles all API requests related to category management
 */

import api from "./api";
import { API_ENDPOINTS } from "../constants/apiEndpoints";

/**
 * Fetch all categories
 */
export const getCategories = async () => {
    const response = await api.get(`${API_ENDPOINTS.CATEGORIES}/`);
    return response.data;
};

/**
 * Create a new category
 */
export const createCategory = async (categoryData) => {
    const response = await api.post(
        `${API_ENDPOINTS.CATEGORIES}/`,
        categoryData,
    );

    return response.data;
};

/**
 * Update an existing category
 */
export const updateCategory = async (
    categoryId,
    categoryData,
) => {
    const response = await api.put(
        `${API_ENDPOINTS.CATEGORIES}/${categoryId}`,
        categoryData,
    );

    return response.data;
};

/**
 * Delete category
 */
export const deleteCategory = async (categoryId) => {
    const response = await api.delete(
        `${API_ENDPOINTS.CATEGORIES}/${categoryId}`,
    );

    return response.data;
};