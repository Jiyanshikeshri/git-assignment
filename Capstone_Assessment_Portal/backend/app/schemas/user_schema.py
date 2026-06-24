from pydantic import BaseModel, EmailStr, Field, field_validator
import re

class UserRegister(BaseModel):
    """
    Schema used when a new student registers
    """
    username: str = Field(
        ...,
        min_length=3,
        max_length=30,
        pattern=r"^[a-zA-Z0-9_]+$", #eg Jiyanshi_Keshri or Jiyanshi123
        description="Unique username for the student containing only letters, numbers, and underscores"
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

    @field_validator("name")
    @classmethod
    def validate_name(cls, value: str) -> str:
        """
        Ensures the name contains only alphabets and spaces
        """
        if not re.fullmatch(r"[A-Za-z ]+", value):
            raise ValueError(
                "Name should contain only alphabets and spaces."
            )
        return value.strip()


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