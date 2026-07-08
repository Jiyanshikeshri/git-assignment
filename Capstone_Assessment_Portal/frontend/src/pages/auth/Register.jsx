/**
 * Registration page for Assessment Portal
 */

import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import AuthLayout from "../../components/auth/AuthLayout";
import AuthInput from "../../components/auth/AuthInput";
import AuthButton from "../../components/auth/AuthButton";

import { validateRegisterForm } from "../../utils/validators";
import { registerUser } from "../../services/authService";

function Register() {
    // Registration Form State
    const [formData, setFormData] = useState({
        username: "",
        name: "",
        email: "",
        password: "",
    });

    const [errors, setErrors] = useState({});
    const [loading, setLoading] = useState(false);
    const [apiError, setApiError] = useState("");
    const navigate = useNavigate();
    
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

    const handleSubmit = async (event) => {

        event.preventDefault();

        const validationErrors = validateRegisterForm(formData);

        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }

        setErrors({});

        setApiError("");

        try {
            setLoading(true);
            const response = await registerUser(formData);
            console.log(response);
            alert("Registration Successful!");
            navigate("/login");

        }
        catch (error) {
            if (error.response) {
                setApiError(
                    error.response.data.detail ||
                    "Registration failed."
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
            title="Create Account"
            subtitle="Register to start attempting quizzes."
        >
             <form onSubmit={handleSubmit} noValidate>
                <AuthInput
                    label="Username"
                    name="username"
                    value={formData.username}
                    placeholder="Enter your username"
                    onChange={handleChange}
                    error={errors.username}
                />

                <AuthInput
                    label="Full Name"
                    name="name"
                    value={formData.name}
                    placeholder="Enter your full name"
                    onChange={handleChange}
                    error={errors.name}
                />

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

                {
                    apiError && (
                        <p className="api-error">
                            {apiError}
                        </p>
                    )
                }

                <AuthButton
                    type="submit"
                    text="Register"
                    loading={loading}
                />

                <p className="auth-footer">

                    Already have an account?

                    {" "}

                    <Link to="/login">
                        Login
                    </Link>

                </p>
            </form>
        </AuthLayout>
    );
}

export default Register;