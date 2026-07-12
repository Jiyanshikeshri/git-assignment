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
import StudentCategories from "../pages/student/StudentCategories";
import QuizManagement from "../pages/admin/QuizManagement";
import AllQuizzes from "../pages/admin/AllQuizzes";
import StudentQuizList from "../pages/student/StudentQuizList";
import QuestionManagement from "../pages/admin/QuestionManagement";
import QuestionList from "../pages/admin/QuestionList";
import StudentQuizAttempt from "../pages/student/StudentQuizAttempt";
import StudentResults from "../pages/student/StudentResults";
import StudentResultBreakdown from "../pages/student/StudentResultBreakdown";

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

                    <Route
                        path="/admin/categories/:categoryId/quizzes"
                        element={
                            <ProtectedRoute allowedRole="ADMIN">
                                <QuizManagement />
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/admin/quizzes"
                        element={<AllQuizzes />}
                    />

                    <Route
                        path="/admin/categories/:categoryId/quizzes/:quizId/questions"
                        element={
                            <ProtectedRoute allowedRole="ADMIN">
                                <QuestionManagement />
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/admin/questions"
                        element={<QuestionList />}
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
                    <Route
                        path="/student/categories"
                        element={
                            <ProtectedRoute allowedRole="STUDENT">
                                <StudentCategories />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/student/categories/:categoryId/quizzes"
                        element={
                            <ProtectedRoute allowedRole="STUDENT">
                                <StudentQuizList />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/student/quiz/:quizId"
                        element={
                            <ProtectedRoute allowedRole="STUDENT">
                                <StudentQuizAttempt />
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/student/results"
                        element={
                            <ProtectedRoute allowedRole="STUDENT">
                                <StudentResults />
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/student/results/:resultId"
                        element={
                            <ProtectedRoute allowedRole="STUDENT">
                                <StudentResultBreakdown />
                            </ProtectedRoute>
                        }
                    />
                </Routes>
            </BrowserRouter>
        </>
    );
}

export default AppRoutes;