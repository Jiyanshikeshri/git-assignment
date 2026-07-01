from app.exceptions.custom_exceptions import (
    BadRequestException,
    UnauthorizedException,
)
from app.repositories.user_repository import (
    get_user_by_email,
    get_user_by_username,
    create_user,
)
from app.schemas.user_schema import (
    UserRegister,
    UserLogin,
    TokenResponse,
)   
from app.utils.password import hash_password
from app.config.security import (
    create_access_token, 
    create_refresh_token, 
    decode_access_token,
)
from app.utils.password import verify_password
from app.constants.constants import (
    INVALID_OR_EXPIRED_REFRESH_TOKEN,
    ROLE_STUDENT,
    USERNAME_ALREADY_EXISTS,
    EMAIL_ALREADY_EXISTS,
    STUDENT_REGISTERED_SUCCESSFULLY,
    INVALID_EMAIL_OR_PASSWORD,
)
from app.config.logger import logger


def register_student(user: UserRegister):
    """
    Register a new student after validating the email and hashing the password
    """

    logger.info(
        "Registration request received for email: %s",
        user.email,
    )

    # Checking whether the username is already taken
    existing_username = get_user_by_username(user.username)

    if existing_username:
        logger.warning(
            "Registration failed. Username already exists: %s",
            user.username,
        )   
        raise BadRequestException(
            USERNAME_ALREADY_EXISTS
        )

    existing_user = get_user_by_email(user.email)

    if existing_user:
        logger.warning(
            "Registration failed. Email already registered: %s",
            user.email,
        )
        raise BadRequestException(
            EMAIL_ALREADY_EXISTS
        )

    user_data = {
        "username": user.username,
        "name": user.name,
        "email": user.email,
        "password": hash_password(user.password),
        "role": ROLE_STUDENT
    }

    create_user(user_data)

    logger.info(
        "Student registered successfully. Email: %s",
        user.email,
    )

    return {
        "message": STUDENT_REGISTERED_SUCCESSFULLY
    }


def login_user(user: UserLogin):
    """
    Authenticate a user and generate a JWT token
    """

    logger.info(
        "Login request received for email: %s",
        user.email,
    )

    existing_user = get_user_by_email(user.email)

    if not existing_user:
        logger.warning(
            "Login failed. Email not found: %s",
            user.email,
        )
        raise UnauthorizedException(
            INVALID_EMAIL_OR_PASSWORD
        )

    if not verify_password(user.password, existing_user["password"]):
        logger.warning(
            "Login failed. Invalid password for email: %s",
            user.email,
        )
        raise UnauthorizedException(
            INVALID_EMAIL_OR_PASSWORD
        )

    token = create_access_token(
        {
            "user_id": str(existing_user["_id"]),
            "email": existing_user["email"],
            "role": existing_user["role"]
        }
    )

    refresh_token = create_refresh_token(
        {
            "user_id": str(existing_user["_id"]),
            "email": existing_user["email"],
            "role": existing_user["role"]
        }
    )

    logger.info(
        "User logged in successfully. Email: %s, Role: %s",
        existing_user["email"],
        existing_user["role"],
    )

    return TokenResponse(
        access_token=token,
        refresh_token=refresh_token,
        token_type="bearer",
    )


def refresh_access_token(refresh_token: str):
    """
    Validate the refresh token and issue a new access token.
    """

    logger.info(
        "Refresh token request received."
    )

    try:
        payload = decode_access_token(refresh_token)

        new_access_token = create_access_token(
            {
                "user_id": payload["user_id"],
                "email": payload["email"],
                "role": payload["role"],
            }
        )

        logger.info(
            "Access token refreshed successfully."
        )

        return {
            "access_token": new_access_token,
            "token_type": "bearer",
        }

    except Exception:
        logger.warning(
            "Refresh token validation failed."
        )
        raise UnauthorizedException(
            INVALID_OR_EXPIRED_REFRESH_TOKEN
        )