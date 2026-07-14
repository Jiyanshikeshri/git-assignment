/**
 * Dummy Data for Admin Dashboard
 */

import {
    FaLayerGroup,
    FaClipboardList,
    FaQuestionCircle,
    FaUsers,
    FaChartLine,
} from "react-icons/fa";

export const dashboardStats = [
    {
        title: "Total Categories",
        value: 8,
        icon: <FaLayerGroup />,
    },
    {
        title: "Total Quizzes",
        value: 24,
        icon: <FaClipboardList />,
    },
    {
        title: "Total Questions",
        value: 185,
        icon: <FaQuestionCircle />,
    },
    {
        title: "Total Students",
        value: 42,
        icon: <FaUsers />,
    },
    {
        title: "Total Attempts",
        value: 116,
        icon: <FaChartLine />,
    },
];

export const recentAttempts = [
    {
        student: "Rahul Sharma",
        quiz: "HTML Basics",
        score: "90%",
        date: "09 Jul 2026",
    },
    {
        student: "Priya Singh",
        quiz: "Python Basics",
        score: "82%",
        date: "09 Jul 2026",
    },
    {
        student: "Aman Gupta",
        quiz: "SQL Fundamentals",
        score: "76%",
        date: "08 Jul 2026",
    },
    {
        student: "Neha Verma",
        quiz: "React Essentials",
        score: "94%",
        date: "08 Jul 2026",
    },
];