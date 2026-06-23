from pydantic import BaseModel, EmailStr

class UserRegister(BaseModel):
    """
    Schema used when a new student registers
    """
    name: str
    email: EmailStr
    password: str


class UserLogin(BaseModel):
    """
    Schema used for user login
    """
    email: EmailStr
    password: str


class UserResponse(BaseModel):
    """
    Schema returned in API responses after successful user-related operations
    """
    id: str
    name: str
    email: EmailStr
    role: str