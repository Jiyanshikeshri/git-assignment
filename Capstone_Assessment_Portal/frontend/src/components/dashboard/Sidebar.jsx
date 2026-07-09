/**
 * Reusable Sidebar Component
 * Displays navigation links based on user role
 */

import { NavLink} from "react-router-dom";

import { useAuth } from "../../context/AuthContext";

import "../../styles/dashboard/Sidebar.css";

function Sidebar() {
    const { user } = useAuth();

    const adminMenu = [
        {
            label: "Dashboard",
            path: "/admin/dashboard",
        },
        {
            label: "Categories",
            path: "/admin/categories",
        },
        {
            label: "Quizzes",
            path: "/admin/quizzes",
        },
        {
            label: "Questions",
            path: "/admin/questions",
        },
        {
            label: "Results",
            path: "/admin/results",
        },
    ];

    const studentMenu = [
        {
            label: "Dashboard",
            path: "/student/dashboard",
        },
        {
            label: "Explore Categories",
            path: "/student/categories",
        },
        {
            label: "My Attempts",
            path: "/student/attempts",
        },
        {
            label: "Results",
            path: "/student/results",
        },
    ];

    const menuItems =
        user?.role === "ADMIN"
            ? adminMenu
            : studentMenu;

    return (
        <aside className="sidebar">
            <div className="sidebar-logo">
                Assessment Portal
            </div>

            <nav className="sidebar-menu">
                {
                    menuItems.map((item) => (
                        <NavLink
                            key={item.path}
                            to={item.path}
                            className="sidebar-link"
                        >
                            {item.label}
                        </NavLink>
                    ))
                }
            </nav>
        </aside>
    );
}

export default Sidebar;