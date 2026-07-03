from app.constants.constants import (
    QUIZ_ALREADY_EXISTS,
    QUIZ_CREATED_SUCCESSFULLY,
    CATEGORY_NOT_FOUND,
    QUIZ_NOT_FOUND,
    QUIZ_UPDATED_SUCCESSFULLY,
    QUIZ_DELETED_SUCCESSFULLY,
)

from app.exceptions.custom_exceptions import (
    BadRequestException,
    NotFoundException,
)

from app.repositories.quiz_repository import (
    get_quiz_by_title,
    create_quiz,
    get_all_quizzes,
    get_quiz_by_id,
    update_quiz,
    get_quiz_by_title_except_id,
    delete_quiz,
)

from app.repositories.category_repository import (
    get_category_by_id,
)

from app.schemas.quiz_schema import (
    QuizCreate,
    QuizUpdate,
    QuizResponse,
)

from app.config.logger import logger

from app.schemas.common_schema import MessageResponse


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

    response = MessageResponse(
        message=QUIZ_CREATED_SUCCESSFULLY
    )
    return response


def fetch_all_quizzes():
    """
    Retrieve all quizzes
    """

    logger.info(
        "Fetching all quizzes."
    )

    quizzes = [
        QuizResponse(
            id=str(quiz["_id"]),
            title=quiz["title"],
            description=quiz["description"],
            category_id=quiz["category_id"],
            duration=quiz["duration"],
        )
        for quiz in get_all_quizzes()
    ]

    logger.info(
        "Retrieved %d quizzes.",
        len(quizzes),
    )

    return quizzes


def fetch_quiz_by_id(quiz_id: str):
    """
    Retrieve a quiz by its ID
    """

    logger.info(
        "Fetching quiz. ID: %s",
        quiz_id,
    )

    quiz = get_quiz_by_id(
        quiz_id
    )

    if not quiz:
        logger.warning(
            "Quiz not found. ID: %s",
            quiz_id,
        )
        raise NotFoundException(
            QUIZ_NOT_FOUND
        )

    logger.info(
        "Quiz retrieved successfully. ID: %s",
        quiz_id,
    )

    return QuizResponse(
        id=str(quiz["_id"]),
        title=quiz["title"],
        description=quiz["description"],
        category_id=quiz["category_id"],
        duration=quiz["duration"],
    )


def update_existing_quiz(
    quiz_id: str,
    quiz: QuizUpdate,
):
    """
    Update an existing quiz after validating: quiz exists, category exists and title is unique
    """

    logger.info(
        "Quiz update request received. ID: %s",
        quiz_id,
    )

    existing_quiz = get_quiz_by_id(
        quiz_id
    )

    if not existing_quiz:
        logger.warning(
            "Quiz update failed. Quiz not found: %s",
            quiz_id,
        )
        raise NotFoundException(
            QUIZ_NOT_FOUND
        )

    existing_category = get_category_by_id(
        quiz.category_id
    )

    if not existing_category:
        logger.warning(
            "Quiz update failed. Category not found: %s",
            quiz.category_id,
        )
        raise NotFoundException(
            CATEGORY_NOT_FOUND
        )
    
    title = quiz.title.strip().lower()

    duplicate_quiz = get_quiz_by_title_except_id(
        title,
        quiz_id,
    )

    if duplicate_quiz:
        logger.warning(
            "Quiz update failed. Quiz title already exists: %s",
            quiz.title,
        )
        raise BadRequestException(
            QUIZ_ALREADY_EXISTS
        )

    update_quiz(
        quiz_id,
        {
            "title": title,
            "description": quiz.description.strip(),
            "category_id": quiz.category_id,
            "duration": quiz.duration,
        },
    )

    logger.info(
        "Quiz updated successfully. ID: %s",
        quiz_id,
    )

    response = MessageResponse(
        message=QUIZ_UPDATED_SUCCESSFULLY
    )
    return response


def delete_existing_quiz(quiz_id: str):
    """
    Delete an existing quiz
    """

    logger.info(
        "Quiz delete request received. ID: %s",
        quiz_id,
    )

    existing_quiz = get_quiz_by_id(quiz_id)

    if not existing_quiz:
        logger.warning(
            "Quiz deletion failed. Quiz not found: %s",
            quiz_id,
        )
        raise NotFoundException(
            QUIZ_NOT_FOUND
        )

    delete_quiz(quiz_id)

    logger.info(
        "Quiz deleted successfully. ID: %s",
        quiz_id,
    )

    response = MessageResponse(
        message=QUIZ_DELETED_SUCCESSFULLY
    )
    return response