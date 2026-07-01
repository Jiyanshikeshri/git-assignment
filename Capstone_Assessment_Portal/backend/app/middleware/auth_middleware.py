from fastapi import Depends
from app.exceptions.custom_exceptions import (
    UnauthorizedException,
    ForbiddenException,
)
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer
from app.constants.constants import (
    ROLE_ADMIN,
    ROLE_STUDENT,
    INVALID_OR_EXPIRED_TOKEN,
    ADMIN_ACCESS_REQUIRED,
    STUDENT_ACCESS_REQUIRED,
)
from app.config.logger import logger
from app.config.security import decode_access_token

# Extract the Bearer token from the Authorization header
security = HTTPBearer()


def get_current_user(
    credentials: HTTPAuthorizationCredentials = Depends(security),
):
    """
    Validate the JWT token and return its payload
    """
    try:
        token = credentials.credentials
        payload = decode_access_token(token)
        return payload
    except Exception:
        logger.warning(
            "JWT token validation failed."
        )
        raise UnauthorizedException(
            INVALID_OR_EXPIRED_TOKEN
        )


def require_admin(user=Depends(get_current_user)):
    """
    Allow access only to users with the ADMIN role
    """
    if user.get("role") != ROLE_ADMIN:
        logger.warning(
            "Unauthorized admin access attempt."
        )
        raise ForbiddenException(
            ADMIN_ACCESS_REQUIRED
        )
    return user


def require_student(user=Depends(get_current_user)):
    """
    Allow access only to users with the STUDENT role.
    """
    if user.get("role") != ROLE_STUDENT:
        logger.warning(
            "Unauthorized student access attempt."
        )
        raise ForbiddenException(
            STUDENT_ACCESS_REQUIRED
        )
    return user