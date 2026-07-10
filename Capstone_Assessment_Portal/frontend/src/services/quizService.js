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

/**
 * Create a new quiz
 */
export const createQuiz = async (quizData) => {

    const response = await api.post(
        "/quizzes/",
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
        `/quizzes/${quizId}`,
        quizData,
    );

    return response.data;

};

/**
 * Delete quiz
 */
export const deleteQuiz = async (quizId) => {

    const response = await api.delete(
        `/quizzes/${quizId}`,
    );
    return response.data;
};