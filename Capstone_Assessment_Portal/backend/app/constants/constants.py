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


# Category Messages
CATEGORY_ALREADY_EXISTS = (
    "Category with this name already exists."
)

CATEGORY_CREATED_SUCCESSFULLY = (
    "Category created successfully."
)

CATEGORY_UPDATED_SUCCESSFULLY = (
    "Category updated successfully."
)

CATEGORY_DELETED_SUCCESSFULLY = (
    "Category deleted successfully."
)

CATEGORY_NOT_FOUND = (
    "Category not found."
)


# Quiz Messages
QUIZ_ALREADY_EXISTS = (
    "Quiz with this title already exists."
)
QUIZ_CREATED_SUCCESSFULLY = (
    "Quiz created successfully."
)
QUIZ_UPDATED_SUCCESSFULLY = (
    "Quiz updated successfully."
)
QUIZ_DELETED_SUCCESSFULLY = (
    "Quiz deleted successfully."
)
QUIZ_NOT_FOUND = (
    "Quiz not found."
)

#Question Messages
QUESTION_ALREADY_EXISTS = (
    "Question already exists in this quiz."
)

QUESTION_CREATED_SUCCESSFULLY = (
    "Question created successfully."
)

QUESTION_UPDATED_SUCCESSFULLY = (
    "Question updated successfully."
)

QUESTION_DELETED_SUCCESSFULLY = (
    "Question deleted successfully."
)

QUESTION_NOT_FOUND = (
    "Question not found."
)

# Attempt Status

ATTEMPT_STATUS_IN_PROGRESS = "IN_PROGRESS"

ATTEMPT_STATUS_SUBMITTED = "SUBMITTED"

ATTEMPT_STATUS_EXPIRED = "EXPIRED"


# Attempt Messages

ATTEMPT_STARTED_SUCCESSFULLY = (
    "Quiz attempt started successfully."
)

ATTEMPT_ALREADY_EXISTS = (
    "An active quiz attempt already exists."
)

ATTEMPT_NOT_FOUND = (
    "Quiz attempt not found."
)

ATTEMPT_ALREADY_SUBMITTED = (
    "Quiz has already been submitted."
)

ATTEMPT_EXPIRED = (
    "Quiz attempt has expired."
)

MAX_ATTEMPT_LIMIT_REACHED = (
    "Maximum attempt limit reached for this quiz."
)

QUIZ_HAS_NO_QUESTIONS = (
    "Cannot start attempt. Quiz has no questions."
)

ANSWER_SAVED_SUCCESSFULLY = (
    "Answer saved successfully."
)

QUESTION_NOT_FOUND_IN_ATTEMPT = (
    "Question not found in attempt."
)

INVALID_SELECTED_ANSWER = (
    "Selected answer is not valid for this question."
)

QUIZ_SUBMITTED_SUCCESSFULLY = (
    "Quiz submitted successfully."
)