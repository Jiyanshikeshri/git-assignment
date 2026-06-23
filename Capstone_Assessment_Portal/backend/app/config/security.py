from datetime import datetime, timedelta, timezone
import os

from dotenv import load_dotenv
from jose import jwt

load_dotenv()

# Reads JWT configuration from env file
SECRET_KEY = os.getenv("JWT_SECRET_KEY")
ALGORITHM = os.getenv("JWT_ALGORITHM")
ACCESS_TOKEN_EXPIRE_MINUTES = int(
    os.getenv("ACCESS_TOKEN_EXPIRE_MINUTES", 60)
)


def create_access_token(data: dict) -> str:
    """
    It is used after successful login
    Generates a JWT token with an expiry time, the payload containing the user id, email, and role
    """
    token_data = data.copy()

    expire = datetime.now(timezone.utc) + timedelta(
        minutes=ACCESS_TOKEN_EXPIRE_MINUTES
    )

    token_data["exp"] = expire

    return jwt.encode(token_data, SECRET_KEY, algorithm=ALGORITHM)


def decode_access_token(token: str) -> dict:
    """
    It is used to validate requests and implement RBAC
    Decode the JWT token and return its payload
    """
    return jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])