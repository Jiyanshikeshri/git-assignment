from app.config.logger import logger

from app.constants.constants import (
    PASSING_PERCENTAGE,
    RESULT_NOT_FOUND,
)

from app.repositories.result_repository import (
    create_result,
    get_latest_result,
    get_student_results,
)

from app.exceptions.custom_exceptions import (
    NotFoundException,
)

from app.schemas.result_schema import (
    ResultStatus,
    ResultResponse,
    QuestionResultResponse,
    ResultHistoryResponse,
)


def calculate_percentage(
    score: int,
    total_questions: int,
):
    """
    Calculate the percentage score obtained in the quiz
    """

    if total_questions == 0:
        return 0.0

    return round(
        (score / total_questions) * 100,
        2,
    )


def get_result_status(
    percentage: float,
):
    """
    Determine whether the student has passed or failed
    """

    if percentage >= PASSING_PERCENTAGE:
        return ResultStatus.PASS.value

    return ResultStatus.FAIL.value


def build_question_result(
    attempt: dict,
):
    """
    Build per-question result breakdown
    """

    submitted_answers = {
        answer["question_id"]: answer["selected_answer"]
        for answer in attempt.get(
            "answers",
            [],
        )
    }

    question_results = []

    for question in attempt[
        "question_snapshot"
    ]:

        selected_answer = submitted_answers.get(
            question["question_id"]
        )

        is_correct = (
            selected_answer
            == question["correct_answer"]
        )

        question_results.append(
            {
                "question_id": question[
                    "question_id"
                ],
                "selected_answer": selected_answer,
                "correct_answer": question[
                    "correct_answer"
                ],
                "is_correct": is_correct,
                "score": 1 if is_correct else 0,
            }
        )

    return question_results


def generate_result(
    attempt: dict,
    score: int,
    correct_answers: int,
    total_questions: int,
    submitted_at,
):
    """
    Generate and store the quiz result after a successful submission
    """

    logger.info(
        "Generating result for attempt ID: %s",
        attempt["_id"],
    )

    percentage = calculate_percentage(
        score,
        total_questions,
    )

    result_status = get_result_status(
        percentage,
    )

    question_results = build_question_result(
        attempt,
    )

    result_data = {
        "attempt_id": str(
            attempt["_id"]
        ),
        "student_id": attempt[
            "student_id"
        ],
        "quiz_id": attempt[
            "quiz_id"
        ],
        "score": score,
        "correct_answers": correct_answers,
        "total_questions": total_questions,
        "percentage": percentage,
        "result_status": result_status,
        "submitted_at": submitted_at,
        "questions": question_results,
    }

    result = create_result(
        result_data
    )

    logger.info(
        "Result generated successfully. Result ID: %s",
        result.inserted_id,
    )

    return result


def get_latest_student_result(
    student_id: str,
):
    """
    Retrieve the latest quiz result for a student
    """

    logger.info(
        "Fetching latest result for student ID: %s",
        student_id,
    )

    result = get_latest_result(
        student_id
    )

    if not result:

        logger.warning(
            "No result found for student ID: %s",
            student_id,
        )

        raise NotFoundException(
            RESULT_NOT_FOUND
        )

    questions = []

    for question in result[
        "questions"
    ]:

        questions.append(
            QuestionResultResponse(
                question_id=question[
                    "question_id"
                ],
                selected_answer=question[
                    "selected_answer"
                ],
                correct_answer=question[
                    "correct_answer"
                ],
                is_correct=question[
                    "is_correct"
                ],
                score=question[
                    "score"
                ],
            )
        )

    logger.info(
        "Latest result fetched successfully."
    )

    return ResultResponse(
        id=str(result["_id"]),
        attempt_id=result["attempt_id"],
        quiz_id=result["quiz_id"],
        student_id=result["student_id"],
        score=result["score"],
        correct_answers=result["correct_answers"],
        total_questions=result["total_questions"],
        percentage=result["percentage"],
        result_status=ResultStatus(result["result_status"]),
        submitted_at=result["submitted_at"],
        questions=questions,
    )


def get_student_result_history(
    student_id: str,
):
    """
    Retrieve the complete quiz result history for a student
    """

    logger.info(
        "Fetching result history for student ID: %s",
        student_id,
    )

    results = list(
        get_student_results(
            student_id
        )
    )

    if not results:

        logger.warning(
            "No results found for student ID: %s",
            student_id,
        )

        raise NotFoundException(
            RESULT_NOT_FOUND
        )

    history = []

    for result in results:

        history.append(
            ResultHistoryResponse(
                id=str(
                    result["_id"]
                ),
                quiz_id=result[
                    "quiz_id"
                ],
                score=result[
                    "score"
                ],
                percentage=result[
                    "percentage"
                ],
                result_status=ResultStatus(
                    result[
                        "result_status"
                    ]
                ),
                submitted_at=result[
                    "submitted_at"
                ],
            )
        )

    logger.info(
        "Result history fetched successfully."
    )

    return history