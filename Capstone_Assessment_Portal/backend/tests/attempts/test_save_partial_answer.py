import uuid

from bson import ObjectId

from datetime import datetime, timedelta, UTC

from app.config.database import db

from app.constants.constants import (
    ANSWER_SAVED_SUCCESSFULLY,
    ATTEMPT_NOT_FOUND,
    ATTEMPT_ALREADY_SUBMITTED,
    ATTEMPT_EXPIRED,
)

def test_save_partial_answer_success(
    client,
    admin_token,
    student_token,
):
    """
    Verify that a student can save an answer successfully.
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
            "name": category_name,
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

    questions = client.get(
        f"/questions/quiz/{quiz_id}",
        headers=admin_headers,
    ).json()

    question_id = next(
        (
            question["id"]
            for question in questions
            if question["question_text"].lower()
            == f"What is Python? {unique}".lower()
        ),
        None,
    )

    assert question_id is not None

    attempt = client.post(
        "/attempts/start",
        headers=student_headers,
        json={
            "quiz_id": quiz_id,
        },
    ).json()

    attempt_id = attempt["attempt_id"]

    response = client.patch(
        f"/attempts/{attempt_id}/answer",
        headers=student_headers,
        json={
            "question_id": question_id,
            "selected_answer": "Language",
        },
    )

    assert response.status_code == 200

    assert (
        response.json()["message"]
        == ANSWER_SAVED_SUCCESSFULLY
    )


def test_save_partial_answer_attempt_not_found(
    client,
    student_token,
):
    """
    Verify that saving an answer for a non-existent attempt returns 404
    """

    student_headers = {
        "Authorization": f"Bearer {student_token}"
    }

    fake_attempt_id = str(
        ObjectId()
    )

    response = client.patch(
        f"/attempts/{fake_attempt_id}/answer",
        headers=student_headers,
        json={
            "question_id": str(ObjectId()),
            "selected_answer": "Language",
        },
    )

    assert response.status_code == 404

    assert (
        response.json()["detail"]
        == ATTEMPT_NOT_FOUND
    )


def test_save_partial_answer_attempt_already_submitted(
    client,
    admin_token,
    student_token,
):
    """
    Verify that answers cannot be saved after a quiz attempt has been submitted
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
            "name": f"category_{unique}",
        },
    )

    categories = client.get(
        "/categories/",
        headers=admin_headers,
    ).json()

    category_id = next(
        category["id"]
        for category in categories
        if category["name"] == f"category_{unique}".lower()
    )

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
        quiz["id"]
        for quiz in quizzes
        if quiz["title"] == f"quiz_{unique}".lower()
    )

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

    questions = client.get(
        f"/questions/quiz/{quiz_id}",
        headers=admin_headers,
    ).json()

    question_id = questions[0]["id"]

    attempt = client.post(
        "/attempts/start",
        headers=student_headers,
        json={
            "quiz_id": quiz_id,
        },
    ).json()

    attempt_id = attempt["attempt_id"]

    db.attempts.update_one(
        {
            "_id": ObjectId(attempt_id),
        },
        {
            "$set": {
                "status": "SUBMITTED",
            }
        },
    )

    response = client.patch(
        f"/attempts/{attempt_id}/answer",
        headers=student_headers,
        json={
            "question_id": question_id,
            "selected_answer": "Language",
        },
    )

    assert response.status_code == 400

    assert (
        response.json()["detail"]
        == ATTEMPT_ALREADY_SUBMITTED
    )


def test_save_partial_answer_attempt_expired(
    client,
    admin_token,
    student_token,
):
    """
    Verify that answers cannot be saved after the quiz attempt has expired
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
            "name": f"category_{unique}",
        },
    )

    categories = client.get(
        "/categories/",
        headers=admin_headers,
    ).json()

    category_id = next(
        category["id"]
        for category in categories
        if category["name"] == f"category_{unique}".lower()
    )

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
        quiz["id"]
        for quiz in quizzes
        if quiz["title"] == f"quiz_{unique}".lower()
    )

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

    questions = client.get(
        f"/questions/quiz/{quiz_id}",
        headers=admin_headers,
    ).json()

    question_id = questions[0]["id"]

    attempt = client.post(
        "/attempts/start",
        headers=student_headers,
        json={
            "quiz_id": quiz_id,
        },
    ).json()

    attempt_id = attempt["attempt_id"]

    db.attempts.update_one(
        {
            "_id": ObjectId(attempt_id),
        },
        {
            "$set": {
                "expires_at": datetime.now(UTC) - timedelta(minutes=1),
            }
        },
    )

    response = client.patch(
        f"/attempts/{attempt_id}/answer",
        headers=student_headers,
        json={
            "question_id": question_id,
            "selected_answer": "Language",
        },
    )

    assert response.status_code == 400

    assert (
        response.json()["detail"]
        == ATTEMPT_EXPIRED
    )