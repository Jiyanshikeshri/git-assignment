/**
 * Reusable Dashboard Card
 */

import "./DashboardCard.css";

function DashboardCard({
    title,
    value,
    icon,
}) {

    return (
        <div className="dashboard-card">

            <div className="dashboard-card-icon">
                {icon}
            </div>

            <div className="dashboard-card-content">

                <h3>
                    {title}
                </h3>

                <p>
                    {value}
                </p>

            </div>

        </div>
    );
}

export default DashboardCard;