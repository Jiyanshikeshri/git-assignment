from app.config.database import db


def get_quiz_by_title(title: str):
    """
    Retrieve a quiz by its title
    """

    quiz = db.quizzes.find_one(
        {
            "title": title
        }
    )

    return quiz


def create_quiz(quiz_data: dict):
    """
    Insert a new quiz into the database
    """

    result = db.quizzes.insert_one(
        quiz_data
    )

    return result


def get_all_quizzes():
    """
    Retrieve all quizzes from the database
    """

    quizzes = db.quizzes.find().sort("title", 1)

    return quizzes