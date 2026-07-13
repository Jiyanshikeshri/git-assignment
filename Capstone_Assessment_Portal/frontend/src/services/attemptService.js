import api from "./api";
import { API_ENDPOINTS } from "../constants/apiEndpoints";

/**
 * Start Quiz Attempt
 */
export const startAttempt = async (quizId) => {

    const response = await api.post(
        API_ENDPOINTS.ATTEMPTS.START,
        {
            quiz_id: quizId,
        },
    );

    return response.data;
};

/**
 * Resume Attempt
 */
export const getAttempt = async (attemptId) => {

    const response = await api.get(
        `${API_ENDPOINTS.ATTEMPTS.BASE}/${attemptId}`,
    );

    return response.data;
};

/**
 * Save Answer
 */
export const saveAnswer = async (
    attemptId,
    questionId,
    answer,
) => {

    const response = await api.patch(
        `${API_ENDPOINTS.ATTEMPTS.BASE}/${attemptId}/answer`,
        {
            question_id: questionId,
            selected_answer: answer,
        },
    );

    return response.data;
};

/**
 * Submit Attempt
 */
export const submitAttempt = async (
    attemptId,
) => {

    const response = await api.patch(
        `${API_ENDPOINTS.ATTEMPTS.BASE}/${attemptId}/submit`,
    );

    return response.data;
};