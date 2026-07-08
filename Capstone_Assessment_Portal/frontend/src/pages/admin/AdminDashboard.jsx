/**
 * Admin Dashboard
 */

import { useAuth } from "../../context/AuthContext";

function AdminDashboard() {
    const { user } = useAuth();
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
        </div>
    );
}

export default AdminDashboard;