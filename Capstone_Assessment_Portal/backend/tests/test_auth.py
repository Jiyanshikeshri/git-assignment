import time


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


def test_login_student(client):
    """
    Verify that a registered student can log in
    and receive a JWT access token.
    """

    import time

    unique_id = str(int(time.time()))

    # Register a new student first
    register_payload = {
        "username": f"loginuser_{unique_id}",
        "name": "Login Test User",
        "email": f"login_{unique_id}@example.com",
        "password": "Password123"
    }

    client.post("/auth/register", json=register_payload)

    # Now attempt login
    login_payload = {
        "email": register_payload["email"],
        "password": register_payload["password"]
    }

    response = client.post("/auth/login", json=login_payload)

    assert response.status_code == 200

    response_data = response.json()
    assert "access_token" in response_data
    assert response_data["token_type"] == "bearer"

