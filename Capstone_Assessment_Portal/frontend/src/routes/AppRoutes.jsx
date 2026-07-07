/**
 * Defines all application routes for the Assessment Portal
 */

import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

// Authentication Pages
import Login from "../pages/auth/Login";
import Register from "../pages/auth/Register";

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

                </Routes>
            </BrowserRouter>
        </>
    );
}

export default AppRoutes;