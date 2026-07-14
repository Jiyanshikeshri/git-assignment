import api from "./api";
import { API_ENDPOINTS } from "../constants/apiEndpoints";

/**
 * Latest Result
 */
export const getLatestResult = async () => {
    const response = await api.get(API_ENDPOINTS.RESULTS.LATEST);
    return response.data;
};

/**
 * Result History
 */
export const getResultHistory = async () => {
    const response = await api.get(API_ENDPOINTS.RESULTS.HISTORY);
    return response.data;
};

/**
 * Result Breakdown
 */
export const getResultBreakdown = async (resultId) => {
    const response = await api.get(`${API_ENDPOINTS.RESULTS.BASE}/${resultId}`,);
    return response.data;
};

/**
 * Admin Results
 */
export const getAllResults = async () => {
    const response = await api.get(API_ENDPOINTS.RESULTS.BASE);
    return response.data;
};