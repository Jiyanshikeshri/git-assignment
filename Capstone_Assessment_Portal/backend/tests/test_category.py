import time


def get_admin_token(client):
    """
    Login as admin and returns the JWT access token
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


def test_create_category(client):
    """
    Verifies that an admin can create a new category successfully
    """

    token = get_admin_token(client)

    unique = str(int(time.time()))

    response = client.post(
        "/categories/",
        headers={
            "Authorization": f"Bearer {token}"
        },
        json={
            "name": f"python_{unique}"
        }
    )

    assert response.status_code == 201
    assert response.json()["message"] == "Category created successfully."