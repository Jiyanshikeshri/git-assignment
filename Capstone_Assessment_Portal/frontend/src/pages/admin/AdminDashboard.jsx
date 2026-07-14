/**
 * Admin Dashboard
 */

import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

function AdminDashboard() {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    /**
     * Handles user logout
     */
    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    return (
        <div
            style={{
                padding: "40px",
            }}
        >
            <h1>
                Admin Dashboard
            </h1>
            <hr />
            <p>
                <strong>Email :</strong>
                {" "}
                {user?.email}
            </p>

            <p>
                <strong>Role :</strong>
                {" "}
                {user?.role}
            </p>
            <button
                onClick={handleLogout}
                style={{
                    marginTop: "20px",
                    padding: "10px 18px",
                    cursor: "pointer",
                }}
            >
                Logout
            </button>
        </div>
    );
}

export default AdminDashboard;