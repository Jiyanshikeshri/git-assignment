from app.config.database import db


def create_attempt(attempt_data: dict):
    """
    Insert a new quiz attempt into the database
    """

    result = db.attempts.insert_one(
        attempt_data
    )

    return result