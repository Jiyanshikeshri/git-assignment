/**
 * Reusable input component for authentication forms
 */

import { useState } from "react";
import { FaEye, FaEyeSlash } from "react-icons/fa";

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

    const [showPassword, setShowPassword] = useState(false);

    const togglePasswordVisibility = () => {
        setShowPassword((previous) => !previous);
    };

    return (
        <div className="input-group">

            <label htmlFor={name} className="input-label">
                {label}
            </label>

            <div className="input-wrapper">
                <input
                    id={name}
                    className={`auth-input ${error ? "input-error" : ""}`}
                    type={
                        type === "password"
                            ? (showPassword ? "text" : "password")
                            : type
                    }
                    name={name}
                    value={value}
                    placeholder={placeholder}
                    onChange={onChange}

                    autoComplete="off"
                    noValidate
                />
                {
                    type === "password" && (
                        <button
                            type="button"
                            className="password-toggle"
                            onClick={togglePasswordVisibility}
                        >
                            {
                                showPassword
                                    ? <FaEyeSlash />
                                    : <FaEye />
                            }
                        </button>
                    )
                }
            </div>

            {error && (
                <p className="error-message">
                    {error}
                </p>
            )}

        </div>
    );
}

export default AuthInput;