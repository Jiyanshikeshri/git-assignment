import api from "./api";

/**
 * Latest Result
 */
export const getLatestResult = async () => {
    const response = await api.get("/results/latest");
    return response.data;
};

/**
 * Result History
 */
export const getResultHistory = async () => {
    const response = await api.get("/results/history");
    return response.data;
};

/**
 * Result Breakdown
 */
export const getResultBreakdown = async (resultId) => {
    const response = await api.get(`/results/${resultId}`);
    return response.data;
};