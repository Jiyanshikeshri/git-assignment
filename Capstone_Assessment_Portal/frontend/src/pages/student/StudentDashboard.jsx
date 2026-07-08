/**
 * Student Dashboard
 */

import { useAuth } from "../../context/AuthContext";

function StudentDashboard() {
    const { user } = useAuth();
    return (
        <div
            style={{
                padding: "40px",
            }}
        >
            <h1>
                Student Dashboard
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

export default StudentDashboard;