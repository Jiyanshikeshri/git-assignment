/**
 * Admin Dashboard
 */

import DashboardLayout from "../../pages/layouts/DashboardLayout";
import DashboardCard from "../../components/dashboard/DashboardCard";
import AttemptsTable from "../../components/dashboard/AttemptsTable";
import {
    dashboardStats,
    recentAttempts,
} from "../../data/dashboard/adminDashboardData";
import "../../styles/dashboard/AdminDashboard.css";

function AdminDashboard() {
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
                    dashboardStats.map((card) => (
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
                attempts={recentAttempts}
            />
        </DashboardLayout>
    );

}

export default AdminDashboard;