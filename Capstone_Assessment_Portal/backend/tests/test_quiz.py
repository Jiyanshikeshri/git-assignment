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


def test_get_all_quizzes(client, admin_token):
    """
    Verifies that an authenticated user can retrieve all quizzes
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

    categories = categories_response.json()

    category_id = None

    for category in categories:
        if category["name"] == category_name.lower():
            category_id = category["id"]
            break

    assert category_id is not None

    create_quiz_response = client.post(
        "/quizzes/",
        headers=headers,
        json={
            "title": f"quiz_{unique}",
            "description": "Python fundamentals quiz",
            "category_id": category_id,
            "duration": 30
        }
    )

    assert create_quiz_response.status_code == 201

    response = client.get(
        "/quizzes/",
        headers=headers
    )

    assert response.status_code == 200

    quizzes = response.json()

    assert isinstance(quizzes, list)
    assert len(quizzes) > 0

    quiz = next(
        (
            quiz for quiz in quizzes
            if quiz["title"] == f"quiz_{unique}".lower()
        ),
        None,
    )

    assert quiz is not None
    assert "id" in quiz
    assert quiz["description"] == "Python fundamentals quiz"
    assert quiz["category_id"] == category_id
    assert quiz["duration"] == 30


def test_get_quiz_by_id(client, admin_token):
    """
    Verifies that a quiz can be retrieved by its ID
    """

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    unique = uuid.uuid4().hex[:8]

    category_name = f"python_{unique}"

    client.post(
        "/categories/",
        headers=headers,
        json={
            "name": category_name
        }
    )

    categories = client.get(
        "/categories/",
        headers=headers
    ).json()

    category_id = next(
        (
            category["id"]
            for category in categories
            if category["name"] == category_name.lower()
        ),
        None,
    )

    assert category_id is not None

    title = f"quiz_{unique}"

    create_response = client.post(
        "/quizzes/",
        headers=headers,
        json={
            "title": title,
            "description": "Python basics quiz",
            "category_id": category_id,
            "duration": 30
        }
    )

    assert create_response.status_code == 201

    quizzes = client.get(
        "/quizzes/",
        headers=headers
    ).json()

    quiz_id = next(
        (
            quiz["id"]
            for quiz in quizzes
            if quiz["title"] == title.lower()
        ),
        None,
    )

    assert quiz_id is not None

    response = client.get(
        f"/quizzes/{quiz_id}",
        headers=headers
    )

    assert response.status_code == 200

    quiz = response.json()

    assert quiz["id"] == quiz_id
    assert quiz["title"] == title.lower()
    assert quiz["description"] == "Python basics quiz"
    assert quiz["category_id"] == category_id
    assert quiz["duration"] == 30


def test_get_non_existing_quiz(client, admin_token):
    """
    Verifies that requesting a non-existing quiz returns 404
    """

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    response = client.get(
        "/quizzes/507f1f77bcf86cd799439011",
        headers=headers,
    )

    assert response.status_code == 404
    assert response.json()["detail"] == QUIZ_NOT_FOUND


def test_update_quiz(client, admin_token):
    """
    Verifies that an admin can update an existing quiz successfully
    """

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    unique = uuid.uuid4().hex[:8]

    category_response = client.post(
        "/categories/",
        headers=headers,
        json={
            "name": f"category_{unique}"
        }
    )

    assert category_response.status_code == 201

    categories = client.get(
        "/categories/",
        headers=headers,
    ).json()

    category_id = None

    for category in categories:
        if category["name"] == f"category_{unique}".lower():
            category_id = category["id"]
            break

    assert category_id is not None

    create_response = client.post(
        "/quizzes/",
        headers=headers,
        json={
            "title": f"quiz_{unique}",
            "description": "Python Basics Quiz",
            "category_id": category_id,
            "duration": 30
        }
    )

    assert create_response.status_code == 201

    quizzes = client.get(
        "/quizzes/",
        headers=headers,
    ).json()

    quiz_id = None

    for quiz in quizzes:
        if quiz["title"] == f"quiz_{unique}".lower():
            quiz_id = quiz["id"]
            break

    assert quiz_id is not None

    update_response = client.put(
        f"/quizzes/{quiz_id}",
        headers=headers,
        json={
            "title": f"updated_quiz_{unique}",
            "description": "Updated Python Quiz",
            "category_id": category_id,
            "duration": 45
        }
    )

    assert update_response.status_code == 200
    assert (
        update_response.json()["message"]
        == QUIZ_UPDATED_SUCCESSFULLY
    )