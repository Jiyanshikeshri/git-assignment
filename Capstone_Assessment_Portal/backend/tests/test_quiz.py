import uuid

from app.constants.constants import (
    QUIZ_ALREADY_EXISTS,
    QUIZ_CREATED_SUCCESSFULLY,
    QUIZ_UPDATED_SUCCESSFULLY,
    QUIZ_DELETED_SUCCESSFULLY,
    QUIZ_NOT_FOUND,
    CATEGORY_NOT_FOUND,
)

def test_create_quiz(client, admin_token):
    """
    Verifies that an admin can create a new quiz successfully
    """

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    unique = uuid.uuid4().hex[:8]

    category_name = f"python_{unique}"

    category_response = client.post(
        "/categories/",
        headers=headers,
        json={
            "name": category_name
        }
    )

    assert category_response.status_code == 201

    categories_response = client.get(
        "/categories/",
        headers=headers
    )

    assert categories_response.status_code == 200

    categories = categories_response.json()

    category_id = None

    for category in categories:
        if category["name"] == category_name.lower():
            category_id = category["id"]
            break

    assert category_id is not None

    quiz_response = client.post(
        "/quizzes/",
        headers=headers,
        json={
            "title": f"quiz_{unique}",
            "description": "Python fundamentals quiz",
            "category_id": category_id,
            "duration": 30
        }
    )

    assert quiz_response.status_code == 201
    assert (
        quiz_response.json()["message"]
        == QUIZ_CREATED_SUCCESSFULLY
    )


def test_create_quiz_invalid_category(client, admin_token):
    """
    Verifies that creating a quiz with an invalid category ID returns 404
    """

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    unique = uuid.uuid4().hex[:8]

    response = client.post(
        "/quizzes/",
        headers=headers,
        json={
            "title": f"quiz_{unique}",
            "description": "Python fundamentals quiz",
            "category_id": "507f1f77bcf86cd799439011",
            "duration": 30
        }
    )

    assert response.status_code == 404
    assert response.json()["detail"] == CATEGORY_NOT_FOUND