from bson import ObjectId

from app.config.database import db


def get_question_by_text_and_quiz(
    question_text: str,
    quiz_id: str,
):
    """
    Retrieve a question by its text within the same quiz
    """

    question = db.questions.find_one(
        {
            "question_text": question_text,
            "quiz_id": quiz_id,
        }
    )

    return question


def create_question(question_data: dict):
    """
    Insert a new question into the database
    """

    result = db.questions.insert_one(
        question_data
    )

    return result


def get_questions_by_quiz_id(quiz_id: str):
    """
    Retrieves all questions belonging to a quiz
    """

    questions = db.questions.find(
        {
            "quiz_id": quiz_id
        }
    )

    return questions