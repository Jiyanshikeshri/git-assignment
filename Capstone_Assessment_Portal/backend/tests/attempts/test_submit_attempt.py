import uuid

from bson import ObjectId

from app.config.database import db

from datetime import (
    datetime,
    timedelta,
    UTC,
)

from app.constants.constants import (
    ATTEMPT_NOT_FOUND,
    ATTEMPT_ALREADY_SUBMITTED,
)

def test_submit_attempt_success(
    client,
    admin_token,
    student_token,
):
    """
    Verify that a student can submit a quiz attempt successfully
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

    client.patch(
        f"/attempts/{attempt_id}/answer",
        headers=student_headers,
        json={
            "question_id": question_id,
            "selected_answer": "Language",
        },
    )

    response = client.patch(
        f"/attempts/{attempt_id}/submit",
        headers=student_headers,
    )

    assert response.status_code == 200

    data = response.json()

    assert data["attempt_id"] == attempt_id
    assert data["quiz_id"] == quiz_id
    assert data["status"] == "SUBMITTED"
    assert data["score"] == 1
    assert data["correct_answers"] == 1
    assert data["total_questions"] == 1
    assert "submitted_at" in data


def test_submit_attempt_not_found(
    client,
    student_token,
):
    """
    Verify that submitting a non-existing attempt returns 404
    """

    student_headers = {
        "Authorization": f"Bearer {student_token}"
    }

    fake_attempt_id = str(
        ObjectId()
    )

    response = client.patch(
        f"/attempts/{fake_attempt_id}/submit",
        headers=student_headers,
    )

    assert response.status_code == 404

    assert (
        response.json()["detail"]
        == ATTEMPT_NOT_FOUND
    )


def test_submit_attempt_already_submitted(
    client,
    admin_token,
    student_token,
):
    """
    Verify that a submitted attempt cannot be submitted again
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
        f"/attempts/{attempt_id}/submit",
        headers=student_headers,
    )

    assert response.status_code == 400

    assert (
        response.json()["detail"]
        == ATTEMPT_ALREADY_SUBMITTED
    )


def test_submit_attempt_after_expiry(
    client,
    admin_token,
    student_token,
):
    """
    Verify that an expired quiz attempt is automatically submitted
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

    client.patch(
        f"/attempts/{attempt_id}/answer",
        headers=student_headers,
        json={
            "question_id": question_id,
            "selected_answer": "Language",
        },
    )

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
        f"/attempts/{attempt_id}/submit",
        headers=student_headers,
    )

    assert response.status_code == 200

    data = response.json()

    assert data["status"] == "SUBMITTED"
    assert data["score"] == 1
    assert data["correct_answers"] == 1
    assert data["total_questions"] == 1

    attempt_doc = db.attempts.find_one(
        {
            "_id": ObjectId(attempt_id),
        }
    )

    assert attempt_doc["status"] == "SUBMITTED"