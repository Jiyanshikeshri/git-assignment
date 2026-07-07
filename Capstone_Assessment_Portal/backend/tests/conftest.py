import pytest
from fastapi.testclient import TestClient

from app.main import app


@pytest.fixture
def client():
    """
    Provides a TestClient instance for API tests
    """
    return TestClient(app)


@pytest.fixture
def admin_token(client):
    """
    Login as the admin user and returns a valid JWT access token
    """

    response = client.post(
        "/auth/login",
        json={
            "email": "admin@gmail.com",
            "password": "Admin@123"
        }
    )

    assert response.status_code == 200

    return response.json()["access_token"]


@pytest.fixture
def student_token(client):
    """
    Login as a student user and return a valid JWT access token
    """

    response = client.post(
        "/auth/login",
        json={
            "email": "test2@gmail.com",
            "password": "12345678",
        },
    )

    assert response.status_code == 200

    return response.json()["access_token"]