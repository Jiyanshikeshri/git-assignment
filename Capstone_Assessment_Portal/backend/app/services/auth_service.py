from fastapi import HTTPException, status

from app.repositories.user_repository import (
    get_user_by_email,
    create_user,
)
from app.schemas.user_schema import UserRegister
from app.utils.password import hash_password
from app.config.security import create_access_token
from app.schemas.user_schema import UserLogin
from app.utils.password import verify_password


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


def login_user(user: UserLogin):
    """
    Authenticate a user and generate a JWT token
    """

    existing_user = get_user_by_email(user.email)

    if not existing_user:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Invalid email or password."
        )

    if not verify_password(user.password, existing_user["password"]):
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Invalid email or password."
        )

    token = create_access_token(
        {
            "user_id": str(existing_user["_id"]),
            "email": existing_user["email"],
            "role": existing_user["role"]
        }
    )

    return {
        "access_token": token,
        "token_type": "bearer"
    }