from app.config.database import db


def create_result(
    result_data: dict,
):
    """
    Insert a generated quiz result into the database
    """

    result = db.results.insert_one(
        result_data
    )

    return result


def get_result_by_attempt_id(
    attempt_id: str,
):
    """
    Retrieve a result using the quiz attempt ID
    """

    result = db.results.find_one(
        {
            "attempt_id": attempt_id
        }
    )

    return result


def get_latest_result(
    student_id: str,
):
    """
    Retrieve the most recent quiz result of a student
    """

    result = db.results.find_one(
        {
            "student_id": student_id
        },
        sort=[
            (
                "submitted_at",
                -1,
            )
        ],
    )

    return result


def get_student_results(
    student_id: str,
):
    """
    Retrieve all quiz results of a student
    """

    results = db.results.find(
        {
            "student_id": student_id
        }
    ).sort(
        "submitted_at",
        -1,
    )

    return results


def get_all_results():
    """
    Retrieve all quiz results
    """

    results = db.results.find().sort(
        "submitted_at",
        -1,
    )

    return results