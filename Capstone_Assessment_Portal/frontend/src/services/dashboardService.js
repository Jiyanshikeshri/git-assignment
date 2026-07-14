import api from "./api";
import { API_ENDPOINTS } from "../constants/apiEndpoints";

/**
 * Admin Dashboard
 */
export const getAdminDashboard = async () => {
    const response = await api.get(API_ENDPOINTS.DASHBOARD.ADMIN);
    return response.data;
};

/**
 * Student Dashboard
 */
export const getStudentDashboard = async () => {
    const response = await api.get(API_ENDPOINTS.DASHBOARD.STUDENT);
    return response.data;
};