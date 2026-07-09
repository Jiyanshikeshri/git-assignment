/**
 * Defines all application routes for the Assessment Portal
 */

import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

// Authentication Pages
import Login from "../pages/auth/Login";
import Register from "../pages/auth/Register";

import ProtectedRoute from "./ProtectedRoute";

import AdminDashboard from "../pages/admin/AdminDashboard";
import StudentDashboard from "../pages/student/StudentDashboard";
import CategoryManagement from "../pages/admin/CategoryManagement";

function AppRoutes() {
    return (
        <>
            <BrowserRouter>
                <Routes>

                    {/* Redirect root URL to Login Page */}
                    <Route
                        path="/"
                        element={<Navigate to="/login" replace />}
                    />

                    {/* Public Routes */}
                    <Route
                        path="/login"
                        element={<Login />}
                    />

                    <Route
                        path="/register"
                        element={<Register />}
                    />

                    {/* Protected Admin Route */}
                    <Route
                        path="/admin/dashboard"
                        element={
                            <ProtectedRoute allowedRole="ADMIN">
                                <AdminDashboard />
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/admin/categories"
                        element={
                            <ProtectedRoute allowedRole="ADMIN">
                                <CategoryManagement />
                            </ProtectedRoute>
                        }
                    />

                    {/* Protected Student Route */}
                    <Route
                        path="/student/dashboard"
                        element={
                            <ProtectedRoute allowedRole="STUDENT">
                                <StudentDashboard />
                            </ProtectedRoute>
                        }
                    />

                </Routes>
            </BrowserRouter>
        </>
    );
}

export default AppRoutes;