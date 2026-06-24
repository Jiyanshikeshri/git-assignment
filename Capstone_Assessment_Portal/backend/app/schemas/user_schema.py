from pydantic import BaseModel, EmailStr, Field

class UserRegister(BaseModel):
    """
    Schema used when a new student registers
    """
    username: str = Field(
        ...,
        min_length=3,
        max_length=30,
        description="Unique username for the student"
    )
    name: str = Field(
        ...,
        min_length=2,
        max_length=50,
        description="Full name of the student"
    )
    email: EmailStr = Field(
        ...,
        description="Email address of the student"
    )
    password: str = Field(
        ...,
        min_length=8,
        max_length=128,
        description="Account password"
    )


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
    username: str
    name: str
    email: EmailStr
    role: str
    

class TokenResponse(BaseModel):
    """
    Response returned after a successful login.
    """
    access_token: str
    token_type: str