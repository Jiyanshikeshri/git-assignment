/**
 * Question Service
 * Handles all API requests related to question management
 */

import api from "./api";

/**
 * Fetch all questions of a quiz
 */
export const getQuestionsByQuiz = async (
    quizId,
) => {

    const response = await api.get(
        `/questions/quiz/${quizId}`,
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
        "/questions/",
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
        `/questions/${questionId}`,
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
        `/questions/${questionId}`,
    );

    return response.data;

};