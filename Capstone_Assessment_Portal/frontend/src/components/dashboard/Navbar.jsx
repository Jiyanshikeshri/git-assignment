/**
 * Reusable Navbar Component
 * Displays current page title and logged-in user details
 */

import { useAuth } from "../../context/AuthContext";

import "../../styles/dashboard/Navbar.css";

function Navbar() {

    const { user } = useAuth();

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
            </div>
        </header>
    );
}

export default Navbar;