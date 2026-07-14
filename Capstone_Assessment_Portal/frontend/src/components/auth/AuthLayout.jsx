/**
 * Reusable layout component for all Authentication pages
 */

import "./AuthLayout.css";

function AuthLayout({ title, subtitle, children }) {
    return (
        <div className="auth-container">

            <div className="auth-left">

                <div className="branding-content">

                    <h1>Assessment Portal</h1>

                    <p>
                        Practice. Assess. Improve.
                    </p>

                </div>

            </div>

            <div className="auth-right">

                <div className="auth-card">

                    <h2>{title}</h2>

                    <p className="auth-subtitle">
                        {subtitle}
                    </p>

                    {children}

                </div>

            </div>

        </div>
    );
}

export default AuthLayout;