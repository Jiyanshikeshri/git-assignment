/**
 * Reusable success message component
 */

import "./SuccessMessage.css";

function SuccessMessage({ message }) {

    if (!message) {
        return null;
    }

    return (
        <div className="success-message">
            <span className="success-icon">
            </span>
            <span>
                {message}
            </span>
        </div>
    );
}

export default SuccessMessage;