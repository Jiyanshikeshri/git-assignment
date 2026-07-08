from app.config.database import db
from bson import ObjectId
from datetime import datetime, timezone

from app.schemas.attempt_schema import AttemptStatus

def create_attempt(attempt_data: dict):
    """
    Insert a new quiz attempt into the database
    """

    result = db.attempts.insert_one(
        attempt_data
    )

    return result


def get_attempt_count(
    student_id: str,
    quiz_id: str,
):
    """
    Returns the number of attempts made by a student for a particular quiz
    """

    count = db.attempts.count_documents(
        {
            "student_id": student_id,
            "quiz_id": quiz_id,
        }
    )
    return count


def get_attempt_by_id(
    attempt_id: str,
):
    """
    Retrieve a quiz attempt by its ID
    """

    attempt = db.attempts.find_one(
        {
            "_id": ObjectId(attempt_id)
        }
    )

    return attempt


def update_attempt_answers(
    attempt_id: str,
    answers: list,
):
    """
    Update the saved answers for a quiz attempt
    """

    result = db.attempts.update_one(
        {
            "_id": ObjectId(attempt_id)
        },
        {
            "$set": {
                "answers": answers
            }
        },
    )

    return result


def submit_attempt(
    attempt_id: str,
    update_data: dict,
):
    """
    Update the attempt after submission
    """

    result = db.attempts.update_one(
        {
            "_id": ObjectId(attempt_id),
        },
        {
            "$set": update_data,
        },
    )

    return result


def expire_attempt(
    attempt_id: str,
    update_data: dict,
):
    """
    Mark an attempt as expired and store evaluation details
    """

    result = db.attempts.update_one(
        {
            "_id": ObjectId(attempt_id),
        },
        {
            "$set": update_data,
        },
    )

    return result


def has_active_attempts(
    quiz_id: str,
):
    """
    Check whether the quiz has any active attempts
    """

    count = db.attempts.count_documents(
        {
            "quiz_id": quiz_id,
            "status": AttemptStatus.IN_PROGRESS.value,
            "expires_at": {
                "$gt": datetime.now(timezone.utc)
            },
        }
    )

    is_active_attempt_present = count > 0

    return is_active_attempt_present


def delete_attempts_by_quiz_id(
    quiz_id: str,
    session=None,
):
    """
    Delete all attempts belonging to a quiz
    """

    result = db.attempts.delete_many(
        {
            "quiz_id": quiz_id
        },
        session=session,
    )

    return result