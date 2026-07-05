from datetime import datetime, timedelta, UTC

from app.config.logger import logger

from app.constants.constants import (
    QUIZ_NOT_FOUND,
    QUIZ_HAS_NO_QUESTIONS,
    MAX_ATTEMPT_LIMIT_REACHED,
)

from app.exceptions.custom_exceptions import (
    NotFoundException,
    BadRequestException,
)

from app.repositories.quiz_repository import (
    get_quiz_by_id,
)

from app.repositories.question_repository import (
    get_questions_by_quiz_id,
)

from app.repositories.attempt_repository import (
    create_attempt,
    get_attempt_count,
)

from app.schemas.attempt_schema import (
    StartAttemptRequest,
    StartAttemptResponse,
    AttemptStatus,
)


def build_question_snapshot(questions):
    """
    Build a snapshot of quiz questions to be stored with the quiz attempt
    """

    snapshot = []

    for question in questions:
        snapshot.append(
            {
                "question_id": str(question["_id"]),
                "question_text": question["question_text"],
                "question_type": question["question_type"],
                "options": question["options"],
                "correct_answer": question["correct_answer"],
                "difficulty": question["difficulty"],
            }
        )

    return snapshot


def start_quiz_attempt(
    attempt: StartAttemptRequest,
    student_id: str,
):
    """
    Start a new quiz attempt for a student
    """

    logger.info(
        "Quiz attempt start request received. Quiz ID: %s Student ID: %s",
        attempt.quiz_id,
        student_id,
    )

    quiz = get_quiz_by_id(
        attempt.quiz_id
    )

    if not quiz:
        logger.warning(
            "Quiz not found. Quiz ID: %s",
            attempt.quiz_id,
        )

        raise NotFoundException(
            QUIZ_NOT_FOUND
        )

    questions = list(
        get_questions_by_quiz_id(
            attempt.quiz_id
        )
    )

    if not questions:
        logger.warning(
            "Quiz has no questions. Quiz ID: %s",
            attempt.quiz_id,
        )

        raise NotFoundException(
            QUIZ_HAS_NO_QUESTIONS
        )
    
    attempt_count = get_attempt_count(
        student_id=student_id,
        quiz_id=attempt.quiz_id,
    )

    if attempt_count >= 2:
        logger.warning(
            "Maximum attempt limit reached. Student ID: %s, Quiz ID: %s",
            student_id,
            attempt.quiz_id,
        )

        raise BadRequestException(
            MAX_ATTEMPT_LIMIT_REACHED
        )

    started_at = datetime.now(
        UTC
    )

    expires_at = started_at + timedelta(
        minutes=quiz["duration"]
    )

    snapshot = build_question_snapshot(
        questions
    )

    attempt_data = {
        "student_id": student_id,
        "quiz_id": attempt.quiz_id,
        "status": AttemptStatus.IN_PROGRESS.value,
        "started_at": started_at,
        "expires_at": expires_at,
        "question_snapshot": snapshot,
    }

    result = create_attempt(
        attempt_data
    )

    logger.info(
        "Quiz attempt started successfully. Attempt ID: %s",
        result.inserted_id,
    )

    return StartAttemptResponse(
        attempt_id=str(result.inserted_id),
        quiz_id=attempt.quiz_id,
        status=AttemptStatus.IN_PROGRESS,
        started_at=started_at,
        expires_at=expires_at,
        total_questions=len(snapshot),
    )