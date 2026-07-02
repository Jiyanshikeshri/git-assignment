from app.constants.constants import (
    QUESTION_ALREADY_EXISTS,
    QUESTION_CREATED_SUCCESSFULLY,
    QUIZ_NOT_FOUND,
)

from app.exceptions.custom_exceptions import (
    BadRequestException,
    NotFoundException,
)

from app.repositories.question_repository import (
    get_question_by_text_and_quiz,
    create_question,
)

from app.repositories.quiz_repository import (
    get_quiz_by_id,
)

from app.schemas.question_schema import (
    QuestionCreate,
)

from app.config.logger import logger

from app.schemas.common_schema import MessageResponse


def create_new_question(question: QuestionCreate):
    """
    Create a new question after validating the quiz and duplicate question
    """

    logger.info(
        "Question creation request received. Question: %s",
        question.question_text,
    )

    question_text = question.question_text.strip().lower()

    existing_question = get_question_by_text_and_quiz(
        question_text,
        question.quiz_id,     
    )   

    if existing_question:
        logger.warning(
            "Question creation failed. Question already exists: %s",
            question_text,
        )
        raise BadRequestException(
            QUESTION_ALREADY_EXISTS
        )

    existing_quiz = get_quiz_by_id(
        question.quiz_id
    )

    if not existing_quiz:
        logger.warning(
            "Question creation failed. Quiz not found: %s",
            question.quiz_id,
        )
        raise NotFoundException(
            QUIZ_NOT_FOUND
        )

    question_data = {
        "quiz_id": question.quiz_id,
        "question_text": question_text,
        "question_type": question.question_type.value,
        "options": question.options,
        "correct_answer": question.correct_answer,
        "difficulty": question.difficulty.value,
        "tags": question.tags,
    }

    create_question(
        question_data
    )

    logger.info(
        "Question created successfully."
    )

    response = MessageResponse(
        message=QUESTION_CREATED_SUCCESSFULLY
    )

    return response