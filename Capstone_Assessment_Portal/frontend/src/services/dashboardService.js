import api from "./api";

/**
 * Admin Dashboard
 */
export const getAdminDashboard = async () => {
    const response = await api.get("/dashboard/admin");
    return response.data;
};

/**
 * Student Dashboard
 */
export const getStudentDashboard = async () => {
    const response = await api.get("/dashboard/student");
    return response.data;
};