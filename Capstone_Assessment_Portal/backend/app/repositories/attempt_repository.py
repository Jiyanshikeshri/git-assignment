from app.config.database import db
from bson import ObjectId


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