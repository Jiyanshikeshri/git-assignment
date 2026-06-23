from fastapi import APIRouter, Depends, status

from app.schemas.user_schema import UserRegister
from app.services.auth_service import register_student
from app.schemas.user_schema import UserLogin
from app.services.auth_service import login_user

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