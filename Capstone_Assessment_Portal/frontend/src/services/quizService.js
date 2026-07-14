/**
 * Quiz Service
 * Handles all API requests related to quiz management
 */

import api from "./api";
import { API_ENDPOINTS } from "../constants/apiEndpoints";

/**
 * Fetch quizzes by category
 */
export const getQuizzesByCategory = async (
    categoryId,
) => {

    const response = await api.get(
        `${API_ENDPOINTS.QUIZZES.CATEGORY}/${categoryId}`,
    );

    return response.data;
};

/**
 * Create a new quiz
 */
export const createQuiz = async (quizData) => {

    const response = await api.post(
        `${API_ENDPOINTS.QUIZZES.BASE}/`,
        quizData,
    );

    return response.data;

};

/**
 * Update quiz
 */
export const updateQuiz = async (
    quizId,
    quizData,
) => {

    const response = await api.put(
        `${API_ENDPOINTS.QUIZZES.BASE}/${quizId}`,
        quizData,
    );

    return response.data;

};

/**
 * Delete quiz
 */
export const deleteQuiz = async (quizId) => {

    const response = await api.delete(
        `${API_ENDPOINTS.QUIZZES.BASE}/${quizId}`,
    );
    return response.data;
};

/**
 * Fetch all quizzes
 */
export const getAllQuizzes = async () => {
    const response = await api.get(
        `${API_ENDPOINTS.QUIZZES.BASE}/`,
    );
    return response.data;
};