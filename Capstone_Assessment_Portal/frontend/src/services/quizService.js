/**
 * Quiz Service
 * Handles all API requests related to quiz management
 */

import api from "./api";

/**
 * Fetch quizzes by category
 */
export const getQuizzesByCategory = async (
    categoryId,
) => {

    const response = await api.get(
        `/quizzes/category/${categoryId}`,
    );

    return response.data;
};