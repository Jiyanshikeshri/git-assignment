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



def test_create_duplicate_category(client, admin_token):
    """
    Verifies that duplicate category names are not allowed
    """

    unique = str(int(time.time()))
    category_name = f"python_{unique}"

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    #Creates the Category for the first time
    response = client.post(
        "/categories/",
        headers=headers,
        json={
            "name": category_name
        }
    )

    assert response.status_code == 201

    # Tries creating the same category again
    duplicate_response = client.post(
        "/categories/",
        headers=headers,
        json={
            "name": category_name
        }
    )

    assert duplicate_response.status_code == 400
    assert (
        duplicate_response.json()["detail"]
        == "Category with this name already exists."
    )