/**
 * Admin Dashboard
 */

import { useEffect, useState } from "react";

import DashboardLayout from "../../layouts/DashboardLayout";
import DashboardCard from "../../components/dashboard/DashboardCard";
import AttemptsTable from "../../components/dashboard/AttemptsTable";

import "../../styles/dashboard/AdminDashboard.css";

import { getAdminDashboard } from "../../services/dashboardService";

import {
    FaLayerGroup,
    FaClipboardList,
    FaQuestionCircle,
    FaUsers,
    FaChartLine,
} from "react-icons/fa";

function AdminDashboard() {
    const [dashboard, setDashboard] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadDashboard();
    }, []);

    const loadDashboard = async () => {
        try {
            const data =
                await getAdminDashboard();
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
            title: "Total Categories",
            value: dashboard.total_categories,
            icon: <FaLayerGroup />,
        },
        {
            title: "Total Quizzes",
            value: dashboard.total_quizzes,
            icon: <FaClipboardList />,
        },
        {
            title: "Total Questions",
            value: dashboard.total_questions,
            icon: <FaQuestionCircle />,
        },
        {
            title: "Total Students",
            value: dashboard.total_students,
            icon: <FaUsers />,
        },
        {
            title: "Total Attempts",
            value: dashboard.total_attempts,
            icon: <FaChartLine />,
        },
    ];

    return (
        <DashboardLayout>
            <h1 className="dashboard-heading">
                Welcome Back, Admin!
            </h1>

            <p className="dashboard-subheading">
                Here's an overview of your assessment portal.
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
            <AttemptsTable
                attempts={dashboard.recent_attempts}
            />
        </DashboardLayout>
    );

}

export default AdminDashboard;