/**
 * Question Service
 * Handles all API requests related to question management
 */

import api from "./api";
import { API_ENDPOINTS } from "../constants/apiEndpoints";

/**
 * Fetch all questions of a quiz
 */
export const getQuestionsByQuiz = async (
    quizId,
) => {

    const response = await api.get(
        `${API_ENDPOINTS.QUESTIONS.QUIZ}/${quizId}`,
    );

    return response.data;

};

/**
 * Create a new question
 */
export const createQuestion = async (
    questionData,
) => {

    const response = await api.post(
        `${API_ENDPOINTS.QUESTIONS.BASE}/`,
        questionData,
    );

    return response.data;

};

/**
 * Update an existing question
 */
export const updateQuestion = async (
    questionId,
    questionData,
) => {

    const response = await api.put(
        `${API_ENDPOINTS.QUESTIONS.BASE}/${questionId}`,
        questionData,
    );

    return response.data;

};

/**
 * Delete a question
 */
export const deleteQuestion = async (
    questionId,
) => {

    const response = await api.delete(
        `${API_ENDPOINTS.QUESTIONS.BASE}/${questionId}`,
    );

    return response.data;

};

/**
 * Fetch all questions
 */
export const getAllQuestions = async () => {

    const response = await api.get(
        API_ENDPOINTS.QUESTIONS.BASE,
    );

    return response.data;

};