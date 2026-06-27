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


def test_get_categories(client, admin_token):
    """
    Verify that an authenticated user can retrieve the list of all categories.
    """

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    #Instead of depending on categories created by previous tests, this test ensures there is at least one category before calling GET /categories
    unique = str(int(time.time()))

    client.post(
        "/categories/",
        headers=headers,
        json={
            "name": f"java_{unique}"
        }
    )

    response = client.get(
        "/categories/",
        headers=headers
    )

    assert response.status_code == 200

    data = response.json()

    assert isinstance(data, list)
    assert len(data) > 0

    assert "id" in data[0]
    assert "name" in data[0]