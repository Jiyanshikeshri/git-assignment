from fastapi import APIRouter, status

from app.schemas.user_schema import UserRegister
from app.services.auth_service import register_student
from app.schemas.user_schema import UserLogin
from app.services.auth_service import login_user

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