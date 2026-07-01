import uuid

from app.constants.constants import (
    CATEGORY_ALREADY_EXISTS,
    CATEGORY_CREATED_SUCCESSFULLY,
    CATEGORY_UPDATED_SUCCESSFULLY,
    CATEGORY_DELETED_SUCCESSFULLY,
    CATEGORY_NOT_FOUND,
)

def test_create_category(client, admin_token):
    """
    Verifies that an admin can create a new category successfully
    """

    unique = uuid.uuid4().hex[:8]

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
    assert response.json()["message"] == CATEGORY_CREATED_SUCCESSFULLY



def test_create_duplicate_category(client, admin_token):
    """
    Verifies that duplicate category names are not allowed
    """

    unique = uuid.uuid4().hex[:8]
    category_name = f"python_{unique}"

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    response = client.post(
        "/categories/",
        headers=headers,
        json={
            "name": category_name
        }
    )

    assert response.status_code == 201

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
        == CATEGORY_ALREADY_EXISTS
    )


def test_get_categories(client, admin_token):
    """
    Verify that an authenticated user can retrieve the list of all categories.
    """

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    unique = uuid.uuid4().hex[:8]

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


def test_update_category(client, admin_token):
    """
    Verifies that an admin can update an existing category successfully
    """

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    unique = uuid.uuid4().hex[:8]

    create_response = client.post(
        "/categories/",
        headers=headers,
        json={
            "name": f"python_{unique}"
        }
    )

    assert create_response.status_code == 201

    categories_response = client.get(
        "/categories/",
        headers=headers
    )

    assert categories_response.status_code == 200

    categories = categories_response.json()

    category_id = None

    for category in categories:
        if category["name"] == f"python_{unique}".lower():
            category_id = category["id"]
            break

    assert category_id is not None

    update_response = client.put(
        f"/categories/{category_id}",
        headers=headers,
        json={
            "name": f"advanced_python_{unique}"
        }
    )

    assert update_response.status_code == 200
    assert (
        update_response.json()["message"]
        == CATEGORY_UPDATED_SUCCESSFULLY
    )


def test_update_non_existing_category(client, admin_token):
    """
    Verify that updating a non-existing category returns 404 Not Found
    """

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    response = client.put(
        "/categories/507f1f77bcf86cd799439011",
        headers=headers,
        json={
            "name": "python"
        }
    )

    assert response.status_code == 404
    assert response.json()["detail"] == CATEGORY_NOT_FOUND



def test_delete_category(client, admin_token):
    """
    Verify that an admin can delete an existing category successfully
    """

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    unique = uuid.uuid4().hex[:8]

    create_response = client.post(
        "/categories/",
        headers=headers,
        json={
            "name": f"delete_{unique}"
        }
    )

    assert create_response.status_code == 201

    categories_response = client.get(
        "/categories/",
        headers=headers
    )

    assert categories_response.status_code == 200

    categories = categories_response.json()

    category_id = None

    for category in categories:
        if category["name"] == f"delete_{unique}".lower():
            category_id = category["id"]
            break

    assert category_id is not None

    delete_response = client.delete(
        f"/categories/{category_id}",
        headers=headers
    )

    assert delete_response.status_code == 200
    assert (
        delete_response.json()["message"]
        == CATEGORY_DELETED_SUCCESSFULLY
    )


def test_delete_non_existing_category(client, admin_token):
    """
    Verifies that deleting a non-existing category returns 404 Not Found
    """

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    response = client.delete(
        "/categories/507f1f77bcf86cd799439011",
        headers=headers
    )

    assert response.status_code == 404
    assert response.json()["detail"] == CATEGORY_NOT_FOUND