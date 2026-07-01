"""
Application-wide constants used across the Project
"""

# User Roles

ROLE_ADMIN = "ADMIN"
ROLE_STUDENT = "STUDENT"


# Authentication Messages

INVALID_EMAIL_OR_PASSWORD = "Invalid email or password."

INVALID_OR_EXPIRED_TOKEN = "Invalid or expired token"

ADMIN_ACCESS_REQUIRED = "Admin access required"

STUDENT_ACCESS_REQUIRED = "Student access required"

INVALID_OR_EXPIRED_REFRESH_TOKEN = (
    "Invalid or expired refresh token"
)


# Registration Messages

USERNAME_ALREADY_EXISTS = "Username is already taken."

EMAIL_ALREADY_EXISTS = "Email is already registered"

STUDENT_REGISTERED_SUCCESSFULLY = (
    "Student registered successfully"
)