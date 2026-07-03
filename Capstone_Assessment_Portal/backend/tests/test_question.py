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


def test_create_true_false_question(client, admin_token):
    """
    Verifies that an admin can create a True/False question successfully
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
            "question_text": f"Python is an interpreted language {unique}?",
            "question_type": "TRUE_FALSE",
            "options": [
                "True",
                "False"
            ],
            "correct_answer": "True",
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


from fastapi import status


def test_create_question_missing_answer(client, admin_token):
    """
    Verifies that creating a question without a correct answer returns a validation error
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
            "difficulty": "EASY",
            "tags": [
                "python"
            ]
        }
    )

    assert response.status_code == status.HTTP_422_UNPROCESSABLE_ENTITY


def test_get_questions_by_quiz(client, admin_token):
    """
    Verifies that all questions for a quiz are returned successfully
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

    create_question_response = client.post(
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

    assert create_question_response.status_code == 201

    response = client.get(
        f"/questions/quiz/{quiz_id}",
        headers=headers,
    )

    assert response.status_code == 200

    questions = response.json()

    assert isinstance(questions, list)
    assert len(questions) > 0

    question = next(
        (
            question
            for question in questions
            if question["question_text"] == f"What is Python {unique}?".lower()
        ),
        None,
    )

    assert question is not None
    assert question["quiz_id"] == quiz_id
    assert question["question_type"] == "MCQ"
    assert question["correct_answer"] == "Language"
    assert question["difficulty"] == "EASY"


from app.constants.constants import (
    QUIZ_NOT_FOUND,
)


def test_get_questions_invalid_quiz(client, admin_token):
    """
    Verifies that fetching questions for a non-existing quiz returns 404
    """

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    response = client.get(
        "/questions/quiz/507f1f77bcf86cd799439011",
        headers=headers,
    )

    assert response.status_code == 404
    assert response.json()["detail"] == QUIZ_NOT_FOUND


from app.constants.constants import (
    QUESTION_UPDATED_SUCCESSFULLY,
)


def test_update_question(client, admin_token):
    """
    Verifies that an admin can update an existing question successfully
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

    create_response = client.post(
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
                "python"
            ]
        }
    )

    assert create_response.status_code == 201

    questions = client.get(
        f"/questions/quiz/{quiz_id}",
        headers=headers,
    ).json()

    question_id = None

    for question in questions:
        if question["question_text"] == f"What is Python {unique}?".lower():
            question_id = question["id"]
            break

    assert question_id is not None

    update_response = client.put(
        f"/questions/{question_id}",
        headers=headers,
        json={
            "quiz_id": quiz_id,
            "question_text": f"Updated Question {unique}",
            "question_type": "MCQ",
            "options": [
                "Java",
                "Python",
                "C++",
                "Go"
            ],
            "correct_answer": "Python",
            "difficulty": "MEDIUM",
            "tags": [
                "updated"
            ]
        }
    )

    assert update_response.status_code == 200
    assert (
        update_response.json()["message"]
        == QUESTION_UPDATED_SUCCESSFULLY
    )


from app.constants.constants import (
    QUESTION_NOT_FOUND,
)


def test_update_non_existing_question(client, admin_token):
    """
    Verifies that updating a non-existing question returns 404
    """

    headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    response = client.put(
        "/questions/507f1f77bcf86cd799439011",
        headers=headers,
        json={
            "quiz_id": "507f1f77bcf86cd799439011",
            "question_text": "Updated Question",
            "question_type": "MCQ",
            "options": [
                "A",
                "B",
                "C",
                "D"
            ],
            "correct_answer": "A",
            "difficulty": "EASY",
            "tags": [
                "python"
            ]
        }
    )

    assert response.status_code == 404
    assert response.json()["detail"] == QUESTION_NOT_FOUND