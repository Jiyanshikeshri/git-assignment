/**
 * Login page for Assessment Portal
 */

import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import AuthLayout from "../../components/auth/AuthLayout";
import AuthInput from "../../components/auth/AuthInput";
import AuthButton from "../../components/auth/AuthButton";

import { validateLoginForm } from "../../utils/validators";
import { loginUser } from "../../services/authService";

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

    // Navigation Hook
    const navigate = useNavigate();

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

        try {
            setLoading(true);
            const response = await loginUser(formData);
            console.log(response);

            /*
                Backend Response will give access token, refresh token and token type
            */
            alert("Login Successful!");

            navigate("/");

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
            <form onSubmit={handleSubmit}>

                <AuthInput
                    label="Email Address"
                    type="email"
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