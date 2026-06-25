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
