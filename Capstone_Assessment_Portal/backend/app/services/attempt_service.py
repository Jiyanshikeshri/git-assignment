from datetime import datetime, timedelta, UTC

from app.config.logger import logger

from app.constants.constants import (
    QUIZ_NOT_FOUND,
    QUIZ_HAS_NO_QUESTIONS,
    MAX_ATTEMPT_LIMIT_REACHED,
    ANSWER_SAVED_SUCCESSFULLY,
    ATTEMPT_ALREADY_SUBMITTED,
    ATTEMPT_EXPIRED,
    ATTEMPT_NOT_FOUND,
    QUESTION_NOT_FOUND_IN_ATTEMPT,
    INVALID_SELECTED_ANSWER,
    QUIZ_SUBMITTED_SUCCESSFULLY,
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
    get_attempt_by_id,
    update_attempt_answers,
    submit_attempt,
)

from app.schemas.attempt_schema import (
    StartAttemptRequest,
    StartAttemptResponse,
    AttemptStatus,
    SaveAnswerRequest,
    ResumeAttemptResponse,
    ResumeQuestionResponse,
    SubmitAttemptResponse,
)

from app.schemas.common_schema import (
    MessageResponse,
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


def evaluate_attempt(
    attempt: dict,
):
    """
    Evaluate the saved answers against the question snapshot
    """

    answers = attempt.get(
        "answers",
        [],
    )

    submitted_answers = {
        answer["question_id"]: answer["selected_answer"]
        for answer in answers
    }

    correct_answers = 0

    for question in attempt["question_snapshot"]:

        if (
            submitted_answers.get(
                question["question_id"]
            )
            == question["correct_answer"]
        ):
            correct_answers += 1

    total_questions = len(
        attempt["question_snapshot"]
    )

    return (
        correct_answers,
        total_questions,
    )


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


def save_partial_answer(
    attempt_id: str,
    answer: SaveAnswerRequest,
    student_id: str,
):
    """
    Save or update a student's answer for a quiz attempt
    """

    logger.info(
        "Save answer request received. Attempt ID: %s",
        attempt_id,
    )

    attempt = get_attempt_by_id(
        attempt_id
    )

    if not attempt:
        logger.warning(
            "Attempt not found. Attempt ID: %s",
            attempt_id,
        )

        raise NotFoundException(
            ATTEMPT_NOT_FOUND
        )

    if attempt["student_id"] != student_id:
        logger.warning(
            "Unauthorized access to attempt. Attempt ID: %s",
            attempt_id,
        )

        raise BadRequestException(
            ATTEMPT_NOT_FOUND
        )

    if (
        attempt["status"]
        == AttemptStatus.SUBMITTED.value
    ):
        logger.warning(
            "Attempt already submitted. Attempt ID: %s",
            attempt_id,
        )

        raise BadRequestException(
            ATTEMPT_ALREADY_SUBMITTED
        )
    

    expires_at = attempt["expires_at"]

    if expires_at.tzinfo is None:
        expires_at = expires_at.replace(
            tzinfo=UTC
        )

    if datetime.now(UTC) > expires_at:
        logger.warning(
            "Attempt expired. Attempt ID: %s",
            attempt_id,
        )

        raise BadRequestException(
            ATTEMPT_EXPIRED
        )

    snapshot_question = next(
        (
            question
            for question in attempt["question_snapshot"]
            if question["question_id"] == answer.question_id
        ),
        None,
    )

    if not snapshot_question:
        logger.warning(
            "Question not found in snapshot. Question ID: %s",
            answer.question_id,
        )

        raise NotFoundException(
            QUESTION_NOT_FOUND_IN_ATTEMPT
        )
    
    if answer.selected_answer not in snapshot_question["options"]:
        logger.warning(
            "Invalid selected answer. Question ID: %s",
            answer.question_id,
        )

        raise BadRequestException(
            INVALID_SELECTED_ANSWER
        )

    answers = attempt.get(
        "answers",
        [],
    )

    answer_updated = False

    for saved_answer in answers:

        if (
            saved_answer["question_id"]
            == answer.question_id
        ):
            saved_answer[
                "selected_answer"
            ] = answer.selected_answer

            answer_updated = True

            break

    if not answer_updated:

        answers.append(
            {
                "question_id": answer.question_id,
                "selected_answer": answer.selected_answer,
            }
        )

    update_attempt_answers(
        attempt_id,
        answers,
    )

    logger.info(
        "Answer saved successfully. Attempt ID: %s",
        attempt_id,
    )

    return MessageResponse(
        message=ANSWER_SAVED_SUCCESSFULLY
    )


def resume_quiz_attempt(
    attempt_id: str,
    student_id: str,
):
    """
    Resume an existing quiz attempt for a student
    """

    logger.info(
        "Resume attempt request received. Attempt ID: %s",
        attempt_id,
    )

    attempt = get_attempt_by_id(
        attempt_id
    )

    if not attempt:
        logger.warning(
            "Attempt not found. Attempt ID: %s",
            attempt_id,
        )

        raise NotFoundException(
            ATTEMPT_NOT_FOUND
        )

    if attempt["student_id"] != student_id:
        logger.warning(
            "Unauthorized access to attempt. Attempt ID: %s",
            attempt_id,
        )

        raise BadRequestException(
            ATTEMPT_NOT_FOUND
        )

    if (
        attempt["status"]
        == AttemptStatus.SUBMITTED.value
    ):
        logger.warning(
            "Attempt already submitted. Attempt ID: %s",
            attempt_id,
        )

        raise BadRequestException(
            ATTEMPT_ALREADY_SUBMITTED
        )

    if (
        datetime.now(UTC).replace(
            tzinfo=None
        ) > attempt["expires_at"]
    ):
        logger.warning(
            "Attempt expired. Attempt ID: %s",
            attempt_id,
        )

        raise BadRequestException(
            ATTEMPT_EXPIRED
        )

    remaining_time = int(
        (
            attempt["expires_at"]
            - datetime.now(UTC).replace(
                tzinfo=None
            )
        ).total_seconds()
    )

    saved_answers = {
        answer["question_id"]: answer["selected_answer"]
        for answer in attempt.get(
            "answers",
            [],
        )
    }

    questions = []

    for question in attempt[
        "question_snapshot"
    ]:
        questions.append(
            ResumeQuestionResponse(
                question_id=question[
                    "question_id"
                ],
                question_text=question[
                    "question_text"
                ],
                question_type=question[
                    "question_type"
                ],
                options=question[
                    "options"
                ],
                difficulty=question[
                    "difficulty"
                ],
                selected_answer=saved_answers.get(
                    question[
                        "question_id"
                    ]
                ),
            )
        )

    logger.info(
        "Quiz attempt resumed successfully. Attempt ID: %s",
        attempt_id,
    )

    return ResumeAttemptResponse(
        attempt_id=attempt_id,
        quiz_id=attempt["quiz_id"],
        status=AttemptStatus(
            attempt["status"]
        ),
        started_at=attempt[
            "started_at"
        ],
        expires_at=attempt[
            "expires_at"
        ],
        remaining_time=remaining_time,
        questions=questions,
    )


def submit_quiz_attempt(
    attempt_id: str,
    student_id: str,
):
    """
    Submit a quiz attempt and calculate the final score.
    If the quiz has already expired, automatically submit it.
    """

    logger.info(
        "Quiz submission request received. Attempt ID: %s",
        attempt_id,
    )

    attempt = get_attempt_by_id(
        attempt_id
    )

    if not attempt:
        logger.warning(
            "Attempt not found. Attempt ID: %s",
            attempt_id,
        )

        raise NotFoundException(
            ATTEMPT_NOT_FOUND
        )

    if attempt["student_id"] != student_id:
        logger.warning(
            "Unauthorized attempt access. Attempt ID: %s",
            attempt_id,
        )

        raise BadRequestException(
            ATTEMPT_NOT_FOUND
        )

    if (
        attempt["status"]
        == AttemptStatus.SUBMITTED.value
    ):
        logger.warning(
            "Attempt already submitted. Attempt ID: %s",
            attempt_id,
        )

        raise BadRequestException(
            ATTEMPT_ALREADY_SUBMITTED
        )
    

    expires_at = attempt["expires_at"]

    if expires_at.tzinfo is None:
        expires_at = expires_at.replace(
            tzinfo=UTC
        )

    current_time = datetime.now(
        UTC
    )

    correct_answers, total_questions = evaluate_attempt(
        attempt
    )

    score = correct_answers

    
    update_data = {
        "status": AttemptStatus.SUBMITTED.value,
        "submitted_at": current_time,
        "score": score,
        "correct_answers": correct_answers,
        "total_questions": total_questions,
    }

    submit_attempt(
        attempt_id,
        update_data,
    )

    if current_time > expires_at:
        logger.info(
            "Attempt auto submitted after expiry. Attempt ID: %s",
            attempt_id,
        )
    else:
        logger.info(
            "Quiz submitted successfully. Attempt ID: %s",
            attempt_id,
        )

        return SubmitAttemptResponse(
            attempt_id=attempt_id,
            quiz_id=attempt["quiz_id"],
            score=score,
            total_questions=total_questions,
            correct_answers=correct_answers,
            status=AttemptStatus.SUBMITTED,
            submitted_at=current_time,
        )

    update_data = {
        "status": AttemptStatus.SUBMITTED.value,
        "submitted_at": current_time,
        "score": score,
        "correct_answers": correct_answers,
        "total_questions": total_questions,
    }

    submit_attempt(
        attempt_id,
        update_data,
    )

    logger.info(
        "Quiz submitted successfully. Attempt ID: %s",
        attempt_id,
    )

    return SubmitAttemptResponse(
        attempt_id=attempt_id,
        quiz_id=attempt["quiz_id"],
        score=score,
        total_questions=total_questions,
        correct_answers=correct_answers,
        status=AttemptStatus.SUBMITTED,
        submitted_at=current_time,
    )


