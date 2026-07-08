/**
 * Login page for Assessment Portal
 */

import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import AuthLayout from "../../components/auth/AuthLayout";
import AuthInput from "../../components/auth/AuthInput";
import AuthButton from "../../components/auth/AuthButton";
import SuccessMessage from "../../components/common/SuccessMessage";

import { validateLoginForm } from "../../utils/validators";
import { loginUser } from "../../services/authService";
import { useAuth } from "../../context/AuthContext";
import { decodeToken } from "../../utils/jwt";

function Login() {
    // Login Form State
    const [formData, setFormData] = useState({
        email: "",
        password: "",
    });

    const [errors, setErrors] = useState({});
    // API Loading State
    const [loading, setLoading] = useState(false);

    // API Error Message
    const [apiError, setApiError] = useState("");

    const [successMessage, setSuccessMessage] = useState("");

    // Navigation Hook
    const navigate = useNavigate();

    const { login } = useAuth();

    /**
     * Handles input field changes
     */
    const handleChange = (event) => {

        const { name, value } = event.target;

        setFormData((previousData) => ({
            ...previousData,
            [name]: value,
        }));

        setErrors((previousErrors) => ({
            ...previousErrors,
            [name]: "",
        }));

        setApiError("");
        setSuccessMessage("");
    };

    /**
     * Handles login form submission
     */
    const handleSubmit = async(event) => {

        event.preventDefault();

        const validationErrors = validateLoginForm(formData);

        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }

        setErrors({});
        setApiError("");
        setSuccessMessage("");

        try {
            setLoading(true);
            const response = await loginUser(formData);
            /*
                Decode access token to get user details
            */
            const user = decodeToken(
                response.access_token
            );

            /*
                Store authentication data globally
            */
            login({
                accessToken: response.access_token,
                refreshToken: response.refresh_token,
                user,
            });

            /*
                Backend Response will give access token, refresh token and token type
            */
            setSuccessMessage("Login successful. Redirecting...");

            setTimeout(() => {
                navigate("/");
            }, 1500);
        }
        catch (error) {
            if (error.response) {
                setApiError(
                    error.response.data.detail ||
                    "Invalid email or password."
                );
            }
            else {
                setApiError(
                    "Unable to connect to server."
                );
            }
        }
        finally {   
            setLoading(false);
        }
    };

    return (
        <AuthLayout
            title="Welcome Back!"
            subtitle="Please login to continue."
        >
            <form onSubmit={handleSubmit} noValidate>

                <AuthInput
                    label="Email Address"
                    type="text"
                    name="email"
                    value={formData.email}
                    placeholder="Enter your email"
                    onChange={handleChange}
                    error={errors.email}
                />

                <AuthInput
                    label="Password"
                    type="password"
                    name="password"
                    value={formData.password}
                    placeholder="Enter your password"
                    onChange={handleChange}
                    error={errors.password}
                />
                {apiError && (
                    <p className="api-error">
                        {apiError}
                    </p>
                )}
                <SuccessMessage
                    message={successMessage}
                />
                <AuthButton
                    type="submit"
                    text="Login"
                    loading={loading}
                />

                <p className="auth-footer">

                    Don't have an account?

                    {" "}

                    <Link to="/register">
                        Register
                    </Link>

                </p>

            </form>
        </AuthLayout>
    );
}

export default Login;