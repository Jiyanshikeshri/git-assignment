import uuid

from app.constants.constants import (
    QUESTION_CREATED_SUCCESSFULLY,
)


def test_create_mcq_question(client, admin_token):
    """
    Verifies that an admin can create an MCQ question successfully
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

    quiz_response = client.post(
        "/quizzes/",
        headers=headers,
        json={
            "title": f"quiz_{unique}",
            "description": "Python Quiz",
            "category_id": category_id,
            "duration": 30
        }
    )

    assert quiz_response.status_code == 201

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

    response = client.post(
        "/questions/",
        headers=headers,
        json={
            "quiz_id": quiz_id,
            "question_text": f"What is Python {unique}?",
            "question_type": "MCQ",
            "options": [
                "Language",
                "Animal",
                "Car",
                "City"
            ],
            "correct_answer": "Language",
            "difficulty": "EASY",
            "tags": [
                "python",
                "basics"
            ]
        }
    )

    assert response.status_code == 201
    assert (
        response.json()["message"]
        == QUESTION_CREATED_SUCCESSFULLY
    )