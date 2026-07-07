/**
 * Registration page for Assessment Portal
 */

import { useState } from "react";
import { Link } from "react-router-dom";

import AuthLayout from "../../components/auth/AuthLayout";
import AuthInput from "../../components/auth/AuthInput";
import AuthButton from "../../components/auth/AuthButton";

function Register() {
    // Registration Form State
    const [formData, setFormData] = useState({
        username: "",
        name: "",
        email: "",
        password: "",
    });
    
    const handleChange = (event) => {

        const { name, value } = event.target;

        setFormData((previousData) => ({
            ...previousData,
            [name]: value,
        }));
    };

    const handleSubmit = (event) => {

        event.preventDefault();

        console.log(formData);
    };

    return (
        <AuthLayout
            title="Create Account"
            subtitle="Register to start attempting quizzes."
        >
             <form onSubmit={handleSubmit}>
                <AuthInput
                    label="Username"
                    name="username"
                    value={formData.username}
                    placeholder="Enter your username"
                    onChange={handleChange}
                />

                <AuthInput
                    label="Full Name"
                    name="name"
                    value={formData.name}
                    placeholder="Enter your full name"
                    onChange={handleChange}
                />

                <AuthInput
                    label="Email Address"
                    type="email"
                    name="email"
                    value={formData.email}
                    placeholder="Enter your email"
                    onChange={handleChange}
                />

                <AuthInput
                    label="Password"
                    type="password"
                    name="password"
                    value={formData.password}
                    placeholder="Enter your password"
                    onChange={handleChange}
                />

                <AuthButton
                    type="submit"
                    text="Register"
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