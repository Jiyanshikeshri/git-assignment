/**
 * Student Dashboard
 */

import { useEffect, useState } from "react";

import {
    FaFolder,
    FaClipboardList,
    FaCheckCircle,
    FaTrophy,
} from "react-icons/fa";

import {
    getStudentDashboard,
} from "../../services/dashboardService";

import DashboardLayout from "../../pages/layouts/DashboardLayout";
import DashboardCard from "../../components/dashboard/DashboardCard";
import ResultsTable from "../../components/dashboard/ResultsTable";

import "../../styles/dashboard/StudentDashboard.css";

function StudentDashboard() {
    const [dashboard, setDashboard] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadDashboard();
    }, []);

    const loadDashboard = async () => {
        try {
            const data =
                await getStudentDashboard();
            setDashboard(data);
        }
        catch (error) {
            console.error(
                "Failed to load dashboard:",
                error,
            );
        }
        finally {
            setLoading(false);
        }
    };

    if (loading) {
        return (
            <DashboardLayout>
                <h2>Loading Dashboard...</h2>
            </DashboardLayout>
        );
    }

    const dashboardCards = [
        {
            title: "Available Categories",
            value: dashboard.total_categories,
            icon: <FaFolder />,
        },
        {
            title: "Available Quizzes",
            value: dashboard.available_quizzes,
            icon: <FaClipboardList />,
        },
        {
            title: "Attempted Quizzes",
            value: dashboard.quizzes_attempted,
            icon: <FaCheckCircle />,
        },
        {
            title: "Average Score",
            value: `${dashboard.average_score}%`,
            icon: <FaTrophy />,
        },
    ];

    return (
        <DashboardLayout>
            <h1 className="dashboard-heading">
                Welcome Back, Student!
            </h1>

            <p className="dashboard-subheading">
                Continue your learning journey and track your progress.
            </p>

            <div className="dashboard-grid">
                {
                    dashboardCards.map((card) => (
                        <DashboardCard
                            key={card.title}
                            title={card.title}
                            value={card.value}
                            icon={card.icon}
                        />
                    ))
                }
            </div>
            <ResultsTable results={dashboard.recent_results} />
        </DashboardLayout>
    );
}

export default StudentDashboard;