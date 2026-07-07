/**
 * Contains reusable client-side validation functions
 */

export const validateLoginForm = (formData) => {

    const errors = {};

    // Email Validation
    if (!formData.email.trim()) {
        errors.email = "Email is required.";
    } else if (
        !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(formData.email)
    ) {
        errors.email = "Please enter a valid email address.";
    }

    // Password Validation
    if (!formData.password.trim()) {
        errors.password = "Password is required.";
    }

    return errors;
};