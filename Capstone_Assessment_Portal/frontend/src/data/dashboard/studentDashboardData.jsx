/**
 * Dummy Data for table and cards for dashboard
 */

import {
    FaFolder,
    FaClipboardList,
    FaCheckCircle,
    FaTrophy,
} from "react-icons/fa";

export const dashboardStats = [
    {
        title: "Available Categories",
        value: 8,
        icon: <FaFolder />,
    },
    {
        title: "Available Quizzes",
        value: 24,
        icon: <FaClipboardList />,
    },
    {
        title: "Attempted Quizzes",
        value: 12,
        icon: <FaCheckCircle />,
    },
    {
        title: "Average Score",
        value: "82%",
        icon: <FaTrophy />,
    },
];

export const recentResults = [
    {
        quiz: "HTML Basics",
        category: "Web Development",
        score: "85%",
        status: "Passed",
    },
    {
        quiz: "Python Fundamentals",
        category: "Programming",
        score: "92%",
        status: "Passed",
    },
    {
        quiz: "SQL Basics",
        category: "Database",
        score: "74%",
        status: "Passed",
    },
    {
        quiz: "React Essentials",
        category: "Frontend",
        score: "48%",
        status: "Failed",
    },
];