from app.constants.constants import (
    QUIZ_ALREADY_EXISTS,
    QUIZ_CREATED_SUCCESSFULLY,
    CATEGORY_NOT_FOUND,
)

from app.exceptions.custom_exceptions import (
    BadRequestException,
    NotFoundException,
)

from app.repositories.quiz_repository import (
    get_quiz_by_title,
    create_quiz,
)

from app.repositories.category_repository import (
    get_category_by_id,
)

from app.schemas.quiz_schema import QuizCreate

from app.config.logger import logger


def create_new_quiz(quiz: QuizCreate):
    """
    Create a new quiz after validating the title and category
    """

    logger.info(
        "Quiz creation request received. Title: %s",
        quiz.title,
    )

    title = quiz.title.strip().lower()

    existing_quiz = get_quiz_by_title(
        title
    )

    if existing_quiz:
        logger.warning(
            "Quiz creation failed. Quiz already exists: %s",
            title,
        )
        raise BadRequestException(
            QUIZ_ALREADY_EXISTS
        )

    existing_category = get_category_by_id(
        quiz.category_id
    )

    if not existing_category:
        logger.warning(
            "Quiz creation failed. Category not found: %s",
            quiz.category_id,
        )
        raise NotFoundException(
            CATEGORY_NOT_FOUND
        )

    quiz_data = {
        "title": title,
        "description": quiz.description.strip(),
        "category_id": quiz.category_id,
        "duration": quiz.duration,
    }

    create_quiz(
        quiz_data
    )

    logger.info(
        "Quiz created successfully: %s",
        title,
    )

    return {
        "message": QUIZ_CREATED_SUCCESSFULLY
    }