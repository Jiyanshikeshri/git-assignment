import time
from datetime import datetime, timedelta, timezone
from jose import jwt
from app.config.security import SECRET_KEY, ALGORITHM

def test_register_student(client):
    """
    Verify that a student can register successfully with valid details
    """

    # Generate a unique username and email for every test run
    unique_id = str(int(time.time()))

    payload = {
        "username": f"testuser_{unique_id}",
        "name": "Test Student",
        "email": f"test_{unique_id}@example.com",
        "password": "Password123"
    }

    response = client.post("/auth/register", json=payload)

    assert response.status_code == 201
    assert response.json()["message"] == "Student registered successfully"



def test_login_admin(client):
    """
    Verifies that the manually created admin can log in and receive a JWT access token
    """

    login_payload = {
        "email": "admin@gmail.com",
        "password": "Admin@123"
    }

    response = client.post("/auth/login", json=login_payload)

    assert response.status_code == 200

    response_data = response.json()
    assert "access_token" in response_data
    assert response_data["token_type"] == "bearer"


def test_login_student(client):
    """
    Verify that a registered student can log in and receive a JWT access token
    """
    unique = str(int(time.time()))

    username = f"student_{unique}"
    email = f"student_{unique}@example.com"

    # Register a student
    register_response = client.post(
        "/auth/register",
        json={
            "username": username,
            "name": "Student Test",
            "email": email,
            "password": "Password123"
        }
    )

    assert register_response.status_code == 201

    # Login with the same credentials
    login_response = client.post(
        "/auth/login",
        json={
            "email": email,
            "password": "Password123"
        }
    )

    assert login_response.status_code == 200

    data = login_response.json()

    assert "access_token" in data
    assert data["token_type"] == "bearer"


def test_login_with_invalid_password(client):
    """
    Verify that login fails with an incorrect password
    """

    unique = str(int(time.time()))
    email = f"invalid_pass_{unique}@example.com"

    # Register a student
    client.post(
        "/auth/register",
        json={
            "username": f"user_{unique}",
            "name": "Invalid Password Test",
            "email": email,
            "password": "Password123"
        }
    )

    # Try logging in with the wrong password
    response = client.post(
        "/auth/login",
        json={
            "email": email,
            "password": "WrongPassword123"
        }
    )

    assert response.status_code == 401
    assert response.json()["detail"] == "Invalid email or password."


def test_login_with_invalid_email(client):
    """
    Verify that login fails when the email is not registered.
    """

    response = client.post(
        "/auth/login",
        json={
            "email": "doesnotexist@example.com",
            "password": "Password123"
        }
    )

    assert response.status_code == 401
    assert response.json()["detail"] == "Invalid email or password."

def test_login_with_invalid_email(client):
    """
    Verify that login fails when the email is not registered
    """

    response = client.post(
        "/auth/login",
        json={
            "email": "doesnotexist@example.com",
            "password": "Password123"
        }
    )

    assert response.status_code == 401
    assert response.json()["detail"] == "Invalid email or password."


def test_access_protected_api_without_token(client):
    """
    Verifies that a protected API cannot be accessed without a JWT token
    """

    response = client.get("/auth/admin/dashboard")

    assert response.status_code == 401


def test_access_protected_api_with_expired_token(client):
    """
    Verify that access is denied when using an expired JWT token
    """

    expired_token = jwt.encode(
        {
            "user_id": "dummy_user_id",
            "email": "expired@example.com",
            "role": "ADMIN",
            "exp": datetime.now(timezone.utc) - timedelta(minutes=5),
        },
        SECRET_KEY,
        algorithm=ALGORITHM,
    )

    response = client.get(
        "/auth/admin/dashboard",
        headers={
            "Authorization": f"Bearer {expired_token}"
        },
    )

    assert response.status_code == 401
    assert response.json()["detail"] == "Invalid or expired token"