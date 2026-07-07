/**
 * Login page for Assessment Portal
 */

import { useState } from "react";
import { Link } from "react-router-dom";

import AuthLayout from "../../components/auth/AuthLayout";
import AuthInput from "../../components/auth/AuthInput";
import AuthButton from "../../components/auth/AuthButton";
import { validateLoginForm } from "../../utils/validators";

function Login() {
    // Login Form State
    const [formData, setFormData] = useState({
        email: "",
        password: "",
    });

    const [errors, setErrors] = useState({});

    /**
     * Handles input field changes
     */
    const handleChange = (event) => {

        const { name, value } = event.target;

        setFormData((previousData) => ({
            ...previousData,
            [name]: value,
        }));
    };

    /**
     * Handles login form submission
     */
    const handleSubmit = (event) => {

        event.preventDefault();

        const validationErrors = validateLoginForm(formData);

        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }

        setErrors({});

        console.log(formData);
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

                <AuthButton
                    type="submit"
                    text="Login"
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