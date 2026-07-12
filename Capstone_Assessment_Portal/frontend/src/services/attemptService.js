import api from "./api";

/**
 * Start Quiz Attempt
 */
export const startAttempt = async (quizId) => {

    const response = await api.post(
        "/attempts/start",
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
        `/attempts/${attemptId}`,
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
        `/attempts/${attemptId}/answer`,
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
        `/attempts/${attemptId}/submit`,
    );

    return response.data;
};