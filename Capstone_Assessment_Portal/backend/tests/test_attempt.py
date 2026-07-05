import uuid

from app.constants.constants import (
    ATTEMPT_STARTED_SUCCESSFULLY,
    QUIZ_NOT_FOUND,
    QUIZ_HAS_NO_QUESTIONS,
    MAX_ATTEMPT_LIMIT_REACHED,
)


def test_start_quiz_attempt(
    client,
    admin_token,
    student_token,
):
    """
    Verifies that a student can start a quiz attempt successfully
    """

    admin_headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    student_headers = {
        "Authorization": f"Bearer {student_token}"
    }

    unique = uuid.uuid4().hex[:8]

    category_name = f"category_{unique}"

    client.post(
        "/categories/",
        headers=admin_headers,
        json={
            "name": category_name
        },
    )

    categories = client.get(
        "/categories/",
        headers=admin_headers,
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

    client.post(
        "/quizzes/",
        headers=admin_headers,
        json={
            "title": f"quiz_{unique}",
            "description": "Python Quiz",
            "category_id": category_id,
            "duration": 30,
        },
    )

    quizzes = client.get(
        "/quizzes/",
        headers=admin_headers,
    ).json()

    quiz_id = next(
        (
            quiz["id"]
            for quiz in quizzes
            if quiz["title"] == f"quiz_{unique}".lower()
        ),
        None,
    )

    assert quiz_id is not None

    client.post(
        "/questions/",
        headers=admin_headers,
        json={
            "quiz_id": quiz_id,
            "question_text": f"What is Python? {unique}",
            "question_type": "MCQ",
            "options": [
                "Language",
                "Animal",
                "Car",
                "City",
            ],
            "correct_answer": "Language",
            "difficulty": "EASY",
            "tags": [],
        },
    )

    response = client.post(
        "/attempts/start",
        headers=student_headers,
        json={
            "quiz_id": quiz_id,
        },
    )

    assert response.status_code == 201

    data = response.json()

    assert data["quiz_id"] == quiz_id
    assert data["status"] == "IN_PROGRESS"
    assert data["total_questions"] == 1
    assert "attempt_id" in data


def test_start_attempt_invalid_quiz(
    client,
    student_token,
):
    """
    Verifies that starting an attempt for a non-existing quiz returns 404
    """

    headers = {
        "Authorization": f"Bearer {student_token}"
    }

    response = client.post(
        "/attempts/start",
        headers=headers,
        json={
            "quiz_id": "507f1f77bcf86cd799439011"
        },
    )

    assert response.status_code == 404

    assert (
        response.json()["detail"]
        == QUIZ_NOT_FOUND
    )


def test_start_attempt_without_questions(
    client,
    admin_token,
    student_token,
):
    """
    Verifies that a quiz attempt cannot be started when the quiz contains no questions
    """

    admin_headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    student_headers = {
        "Authorization": f"Bearer {student_token}"
    }

    unique = uuid.uuid4().hex[:8]

    client.post(
        "/categories/",
        headers=admin_headers,
        json={
            "name": f"category_{unique}"
        },
    )

    categories = client.get(
        "/categories/",
        headers=admin_headers,
    ).json()

    category_id = next(
        (
            category["id"]
            for category in categories
            if category["name"] == f"category_{unique}".lower()
        ),
        None,
    )

    client.post(
        "/quizzes/",
        headers=admin_headers,
        json={
            "title": f"quiz_{unique}",
            "description": "Sample quiz",
            "category_id": category_id,
            "duration": 20,
        },
    )

    quizzes = client.get(
        "/quizzes/",
        headers=admin_headers,
    ).json()

    quiz_id = next(
        (
            quiz["id"]
            for quiz in quizzes
            if quiz["title"] == f"quiz_{unique}".lower()
        ),
        None,
    )

    response = client.post(
        "/attempts/start",
        headers=student_headers,
        json={
            "quiz_id": quiz_id
        },
    )

    assert response.status_code == 404

    assert (
        response.json()["detail"]
        == QUIZ_HAS_NO_QUESTIONS
    )


def test_attempt_limit_exceeded(
    client,
    admin_token,
    student_token,
):
    """
    Verifies that a student cannot start more than two attempts for the same quiz
    """

    admin_headers = {
        "Authorization": f"Bearer {admin_token}"
    }

    student_headers = {
        "Authorization": f"Bearer {student_token}"
    }

    unique = uuid.uuid4().hex[:8]

    client.post(
        "/categories/",
        headers=admin_headers,
        json={
            "name": f"category_{unique}"
        },
    )

    categories = client.get(
        "/categories/",
        headers=admin_headers,
    ).json()

    category_id = next(
        (
            category["id"]
            for category in categories
            if category["name"] == f"category_{unique}".lower()
        ),
        None,
    )

    assert category_id is not None

    client.post(
        "/quizzes/",
        headers=admin_headers,
        json={
            "title": f"quiz_{unique}",
            "description": "Python Quiz",
            "category_id": category_id,
            "duration": 30,
        },
    )

    quizzes = client.get(
        "/quizzes/",
        headers=admin_headers,
    ).json()

    quiz_id = next(
        (
            quiz["id"]
            for quiz in quizzes
            if quiz["title"] == f"quiz_{unique}".lower()
        ),
        None,
    )

    assert quiz_id is not None

    client.post(
        "/questions/",
        headers=admin_headers,
        json={
            "quiz_id": quiz_id,
            "question_text": f"What is Python? {unique}",
            "question_type": "MCQ",
            "options": [
                "Language",
                "Animal",
                "Car",
                "City",
            ],
            "correct_answer": "Language",
            "difficulty": "EASY",
            "tags": [],
        },
    )

    response = client.post(
        "/attempts/start",
        headers=student_headers,
        json={
            "quiz_id": quiz_id,
        },
    )

    assert response.status_code == 201

    response = client.post(
        "/attempts/start",
        headers=student_headers,
        json={
            "quiz_id": quiz_id,
        },
    )

    assert response.status_code == 201

    response = client.post(
        "/attempts/start",
        headers=student_headers,
        json={
            "quiz_id": quiz_id,
        },
    )

    assert response.status_code == 400

    assert (
        response.json()["detail"]
        == MAX_ATTEMPT_LIMIT_REACHED
    )