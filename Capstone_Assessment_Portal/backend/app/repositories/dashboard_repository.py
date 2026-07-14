from app.config.database import db


def get_total_categories():
    """
    Return total number of categories
    """

    category = db.categories.count_documents({})
    return category


def get_total_quizzes():
    """
    Return total number of quizzes
    """

    quizzes = db.quizzes.count_documents({})
    return quizzes


def get_total_questions():
    """
    Return total number of questions
    """

    questions = db.questions.count_documents({})
    return questions


def get_total_students():
    """
    Return total number of students
    """

    students = db.users.count_documents(
        {
            "role": "STUDENT",
        }
    )
    return students


def get_total_attempts():
    """
    Return total number of quiz attempts
    """

    attempts = db.attempts.count_documents({})
    return attempts


def get_recent_attempts(
    limit: int = 5,
):
    """
    Return recent quiz attempts
    """

    recent_attempt = db.results.find().sort(
        "submitted_at",
        -1,
    ).limit(limit)
    return recent_attempt


def get_student_attempt_count(student_id: str):
    """
    Return total quizzes attempted by a student
    """

    student_attempt = db.results.count_documents(
        {
            "student_id": student_id,
        }
    )
    return student_attempt


def get_student_average_score(student_id: str):
    """
    Calculate average percentage scored by a student
    """

    pipeline = [
        {
            "$match": {
                "student_id": student_id,
            }
        },
        {
            "$group": {
                "_id": None,
                "average_score": {
                    "$avg": "$percentage",
                },
            }
        },
    ]

    result = list(
        db.results.aggregate(
            pipeline,
        )
    )

    if not result:
        return 0.0

    return round(
        result[0]["average_score"],
        2,
    )


def get_student_recent_results(student_id: str):
    """
    Return latest quiz results of a student
    """

    result = db.results.find(
        {
            "student_id": student_id,
        }
    ).sort(
        "submitted_at",
        -1,
    ).limit(5)
    return result