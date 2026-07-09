/**
 * Student Dashboard
 */

import DashboardLayout from "../../layouts/DashboardLayout";
import DashboardCard from "../../components/dashboard/DashboardCard";
import ResultsTable from "../../components/dashboard/ResultsTable";
import {
    dashboardStats,
    recentResults,
} from "../../data/dashboard/studentDashboardData";
import "../../styles/dashboard/StudentDashboard.css";

function StudentDashboard() {
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
            <ResultsTable results={recentResults} />
        </DashboardLayout>
    );
}

export default StudentDashboard;