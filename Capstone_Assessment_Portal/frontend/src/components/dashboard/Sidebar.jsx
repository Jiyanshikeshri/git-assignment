/**
 * Reusable Sidebar Component
 * Displays navigation links based on user role
 */

import { NavLink} from "react-router-dom";

import { useAuth } from "../../context/AuthContext";
import {
    ADMIN_MENU,
    STUDENT_MENU,
} from "../../constants/sidebarMenu";

import "../../styles/dashboard/Sidebar.css";

function Sidebar() {
    const { user } = useAuth();

    const menuItems =
        user?.role === "ADMIN"
            ? ADMIN_MENU
            : STUDENT_MENU;

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