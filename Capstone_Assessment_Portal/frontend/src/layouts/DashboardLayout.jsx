/**
 * Reusable Dashboard Layout
 * Used by both Admin and Student dashboards
 */

import Sidebar from "../components/dashboard/Sidebar";
import Navbar from "../components/dashboard/Navbar";

import "../styles/dashboard/DashboardLayout.css";

function DashboardLayout({ children }) {

    return (
        <div className="dashboard-layout">
            <Sidebar />
            <div className="dashboard-content">
                <Navbar />
                <main className="dashboard-main">
                    {children}
                </main>
            </div>
        </div>
    );
}

export default DashboardLayout;