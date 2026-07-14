/**
 * Contains reusable client-side validation functions
 */

/**
 * Validates Login Form
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


/**
 * Validates Register Form
 */
export const validateRegisterForm = (formData) => {

    const errors = {};

    // Username Validation
    if (!formData.username.trim()) {
        errors.username = "Username is required.";
    } else if (
        formData.username.length < 3 ||
        formData.username.length > 30
    ) {
        errors.username = "Username must be between 3 and 30 characters.";
    } else if (
        !/^[a-zA-Z0-9_]+$/.test(formData.username)
    ) {
        errors.username =
            "Username can contain only letters, numbers and underscores.";
    }

    // Full Name Validation
    if (!formData.name.trim()) {
        errors.name = "Full name is required.";
    } else if (
        !/^[A-Za-z ]+$/.test(formData.name)
    ) {
        errors.name =
            "Full name should contain only alphabets and spaces.";
    }

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
    } else if (formData.password.length < 8) {
        errors.password =
            "Password must be at least 8 characters long.";
    }

    return errors;
};