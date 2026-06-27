import time


def test_create_category(client, admin_token):
    """
    Verifies that an admin can create a new category successfully
    """

    unique = str(int(time.time()))

    response = client.post(
        "/categories/",
        headers={
            "Authorization": f"Bearer {admin_token}"
        },
        json={
            "name": f"python_{unique}"
        }
    )

    assert response.status_code == 201
    assert response.json()["message"] == "Category created successfully."