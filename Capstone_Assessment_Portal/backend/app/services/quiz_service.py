from app.constants.constants import (
    QUIZ_ALREADY_EXISTS,
    QUIZ_CREATED_SUCCESSFULLY,
    CATEGORY_NOT_FOUND,
    QUIZ_NOT_FOUND,
    QUIZ_UPDATED_SUCCESSFULLY,
    QUIZ_DELETED_SUCCESSFULLY,
    QUIZ_HAS_ACTIVE_ATTEMPTS,
    MAX_ATTEMPTS_PER_QUIZ,
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
    get_quizzes_by_category_id,
)

from app.repositories.category_repository import (
    get_category_by_id,
)

from app.repositories.attempt_repository import (
    has_active_attempts,
    delete_attempts_by_quiz_id,
    get_attempt_count,
)

from app.repositories.question_repository import (
    delete_questions_by_quiz_id,
)

from app.repositories.result_repository import (
    delete_results_by_quiz_id,
)

from app.schemas.quiz_schema import (
    QuizCreate,
    QuizUpdate,
    QuizResponse,
)

from app.config.logger import logger

from app.config.database import client

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


def fetch_quizzes_by_category_id(
    category_id: str,
    student_id: str,
):
    """
    Retrieve all quizzes belonging to a category
    """

    logger.info(
        "Fetching quizzes for category ID: %s",
        category_id,
    )

    category = get_category_by_id(
        category_id
    )

    if not category:
        logger.warning(
            "Category not found. ID: %s",
            category_id,
        )

        raise NotFoundException(
            CATEGORY_NOT_FOUND
        )

    quizzes = []

    for quiz in get_quizzes_by_category_id(
        category_id
    ):

        attempt_count = get_attempt_count(
            student_id=student_id,
            quiz_id=str(quiz["_id"]),
        )

        quizzes.append(
            QuizResponse(
                id=str(quiz["_id"]),
                title=quiz["title"],
                description=quiz["description"],
                category_id=quiz["category_id"],
                duration=quiz["duration"],
                attempts_left=max(
                    0,
                    MAX_ATTEMPTS_PER_QUIZ - attempt_count,
                ),
                can_attempt=attempt_count < MAX_ATTEMPTS_PER_QUIZ,
            )
        )

    logger.info(
        "Retrieved %d quizzes for category ID: %s",
        len(quizzes),
        category_id,
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
    
    if has_active_attempts(
        quiz_id
    ):
        logger.warning(
            "Quiz deletion blocked. Active attempts found for quiz ID: %s",
            quiz_id,
        )

        raise BadRequestException(
            QUIZ_HAS_ACTIVE_ATTEMPTS
        )

    with client.start_session() as session:

        with session.start_transaction():

            logger.info(
                "Deleting all results for quiz ID: %s",
                quiz_id,
            )

            delete_results_by_quiz_id(
                quiz_id,
                session=session,
            )

            logger.info(
                "Deleting all attempts for quiz ID: %s",
                quiz_id,
            )

            delete_attempts_by_quiz_id(
                quiz_id,
                session=session,
            )

            logger.info(
                "Deleting all questions for quiz ID: %s",
                quiz_id,
            )

            delete_questions_by_quiz_id(
                quiz_id,
                session=session,
            )

            logger.info(
                "Deleting quiz. ID: %s",
                quiz_id,
            )

            delete_quiz(
                quiz_id,
                session=session,
            )

    logger.info(
        "Quiz deleted successfully. ID: %s",
        quiz_id,
    )

    response = MessageResponse(
        message=QUIZ_DELETED_SUCCESSFULLY
    )
    return response