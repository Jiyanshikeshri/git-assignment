/**
 * Reusable button component for authentication pages
 */

import "./AuthButton.css";

function AuthButton({
    text,
    type = "button",
    loading = false,
}) {
    return (
        <button
            className="auth-button"
            type={type}
            disabled={loading}
        >
            {loading ? "Please wait..." : text}
        </button>
    );
}

export default AuthButton;