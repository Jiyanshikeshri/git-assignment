from app.config.database import db


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