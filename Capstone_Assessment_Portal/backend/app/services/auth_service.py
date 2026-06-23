from fastapi import HTTPException, status

from app.repositories.user_repository import (
    get_user_by_email,
    create_user,
)
from app.schemas.user_schema import UserRegister
from app.utils.password import hash_password


def register_student(user: UserRegister):
    """
    Register a new student after validating the email and hashing the password
    """

    existing_user = get_user_by_email(user.email)

    if existing_user:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Email is already registered"
        )

    user_data = {
        "name": user.name,
        "email": user.email,
        "password": hash_password(user.password),
        "role": "STUDENT"
    }

    create_user(user_data)

    return {
        "message": "Student registered successfully"
    }