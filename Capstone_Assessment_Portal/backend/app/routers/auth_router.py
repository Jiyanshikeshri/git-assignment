from fastapi import APIRouter, Depends, status

from app.schemas.user_schema import UserRegister
from app.services.auth_service import register_student
from app.schemas.user_schema import UserLogin
from app.services.auth_service import login_user
from app.schemas.user_schema import RefreshTokenRequest
from app.services.auth_service import refresh_access_token
from app.services.auth_service import get_public_key

from app.middleware.auth_middleware import (
    require_admin,
    require_student,
)

router = APIRouter(
    prefix="/auth",
    tags=["Authentication"]
)


@router.post("/register", status_code=status.HTTP_201_CREATED)
def register_user(user: UserRegister):
    """
    Register a new student account
    """
    return register_student(user)

@router.post("/login")
def login(user: UserLogin):
    """
    Authenticate an existing user and return a JWT token
    """
    return login_user(user)

@router.post("/refresh")
def refresh_token(request: RefreshTokenRequest):
    """
    Generate a new access token using a valid refresh token.
    """
    return refresh_access_token(request.refresh_token)

@router.get("/admin/dashboard")
def admin_dashboard(current_user=Depends(require_admin)):
    """
    Test endpoint accessible only to users with the ADMIN role
    """
    return {
        "message": "Welcome to the Admin Dashboard",
        "user": current_user
    }


@router.get("/student/dashboard")
def student_dashboard(current_user=Depends(require_student)):
    """
    Test endpoint accessible only to users with the STUDENT role
    """
    return {
        "message": "Welcome to the Student Dashboard",
        "user": current_user
    }


@router.get("/public-key")
def public_key():
    """
    Returns the RSA public key for frontend password encryption
    """
    return get_public_key()