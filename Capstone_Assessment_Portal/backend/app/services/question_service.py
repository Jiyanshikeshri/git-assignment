from app.constants.constants import (
    QUESTION_ALREADY_EXISTS,
    QUESTION_CREATED_SUCCESSFULLY,
    QUIZ_NOT_FOUND,
    QUESTION_UPDATED_SUCCESSFULLY,
    QUESTION_NOT_FOUND,
)

from app.exceptions.custom_exceptions import (
    BadRequestException,
    NotFoundException,
)

from app.repositories.question_repository import (
    get_question_by_text_and_quiz,
    create_question,
    get_questions_by_quiz_id,
    get_question_by_id,
    update_question,
    get_question_by_text_except_id,
)

from app.repositories.quiz_repository import (
    get_quiz_by_id,
)

from app.schemas.question_schema import (
    QuestionCreate,
    QuestionResponse,
    QuestionUpdate,
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


def fetch_questions_by_quiz(
    quiz_id: str,
):
    """
    Retrieve all questions for a given quiz
    """

    logger.info(
        "Fetching questions for quiz. Quiz ID: %s",
        quiz_id,
    )

    existing_quiz = get_quiz_by_id(
        quiz_id
    )

    if not existing_quiz:
        logger.warning(
            "Quiz not found. Quiz ID: %s",
            quiz_id,
        )
        raise NotFoundException(
            QUIZ_NOT_FOUND
        )

    questions = [
        QuestionResponse(
            id=str(question["_id"]),
            quiz_id=question["quiz_id"],
            question_text=question["question_text"],
            question_type=question["question_type"],
            options=question["options"],
            correct_answer=question["correct_answer"],
            difficulty=question["difficulty"],
            tags=question["tags"],
        )
        for question in get_questions_by_quiz_id(
            quiz_id
        )
    ]

    logger.info(
        "Retrieved %d questions for quiz ID: %s",
        len(questions),
        quiz_id,
    )

    return questions


def update_existing_question(
    question_id: str,
    question: QuestionUpdate,
):
    """
    Update an existing question after validating: Question exists, Quiz exists, Duplicate question does not exist within the same quiz
    """

    logger.info(
        "Question update request received. ID: %s",
        question_id,
    )

    existing_question = get_question_by_id(
        question_id
    )

    if not existing_question:
        logger.warning(
            "Question update failed. Question not found: %s",
            question_id,
        )
        raise NotFoundException(
            QUESTION_NOT_FOUND
        )

    existing_quiz = get_quiz_by_id(
        question.quiz_id
    )

    if not existing_quiz:
        logger.warning(
            "Question update failed. Quiz not found: %s",
            question.quiz_id,
        )
        raise NotFoundException(
            QUIZ_NOT_FOUND
        )

    question_text = question.question_text.strip().lower()

    duplicate_question = get_question_by_text_except_id(
        question_text,
        question.quiz_id,
        question_id,
    )

    if duplicate_question:
        logger.warning(
            "Question update failed. Duplicate question exists: %s",
            question_text,
        )
        raise BadRequestException(
            QUESTION_ALREADY_EXISTS
        )

    update_question(
        question_id,
        {
            "quiz_id": question.quiz_id,
            "question_text": question_text,
            "question_type": question.question_type.value,
            "options": question.options,
            "correct_answer": question.correct_answer,
            "difficulty": question.difficulty.value,
            "tags": question.tags,
        },
    )

    logger.info(
        "Question updated successfully. ID: %s",
        question_id,
    )

    return MessageResponse(
        message=QUESTION_UPDATED_SUCCESSFULLY
    )