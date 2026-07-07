/**
 * Reusable input component for authentication forms
 */

import "./AuthInput.css";

function AuthInput({
    label,
    type = "text",
    name,
    value,
    placeholder,
    onChange,
    error,
}) {
    return (
        <div className="input-group">

            <label htmlFor={name} className="input-label">
                {label}
            </label>

            <input
                id={name}
                className={`auth-input ${error ? "input-error" : ""}`}
                type={type}
                name={name}
                value={value}
                placeholder={placeholder}
                onChange={onChange}
            />

            {error && (
                <p className="error-message">
                    {error}
                </p>
            )}

        </div>
    );
}

export default AuthInput;