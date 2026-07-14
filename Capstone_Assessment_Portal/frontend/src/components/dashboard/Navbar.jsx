/**
 * Reusable Navbar Component
 * Displays current page title and logged-in user details
 */

import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";

import "../../styles/dashboard/Navbar.css";

function Navbar() {

    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    return (
        <header className="dashboard-navbar">

            <div className="navbar-left">
                <h2 className="navbar-title">
                    Dashboard
                </h2>
            </div>

            <div className="navbar-right">
                <div className="navbar-user">

                    <p className="navbar-name">
                        {user?.email}
                    </p>

                    <span className="navbar-role">
                        {user?.role}
                    </span>
                </div>
                <button
                    className="navbar-logout"
                    onClick={handleLogout}
                >
                    Logout
                </button>
            </div>
        </header>
    );
}

export default Navbar;