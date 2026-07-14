from app.config.logger import logger

from app.constants.constants import (
    RESULT_NOT_FOUND,
)

from app.exceptions.custom_exceptions import (
    NotFoundException,
)

from app.repositories.dashboard_repository import (
    get_total_categories,
    get_total_quizzes,
    get_total_questions,
    get_total_students,
    get_total_attempts,
    get_recent_attempts,
    get_student_average_score,
    get_student_attempt_count,
    get_student_recent_results,
)

from app.repositories.user_repository import (
    get_user_by_id,
)

from app.repositories.quiz_repository import (
    get_quiz_by_id,
)

from app.repositories.category_repository import (
    get_category_by_id,
)

from app.schemas.dashboard_schema import (
    AdminDashboardResponse,
    RecentAttemptResponse,
    StudentDashboardResponse,
    StudentRecentResultResponse,
)


def get_admin_dashboard():
    """
    Retrieve dashboard statistics and recent quiz attempts
    for the admin dashboard
    """

    logger.info(
        "Fetching admin dashboard statistics."
    )

    recent_results = list(
        get_recent_attempts()
    )

    if not recent_results:

        logger.warning(
            "No quiz attempts found for dashboard."
        )

        raise NotFoundException(
            RESULT_NOT_FOUND
        )

    recent_attempts = []

    for result in recent_results:

        user = get_user_by_id(
            result["student_id"],
        )

        quiz = get_quiz_by_id(
            result["quiz_id"],
        )

        recent_attempts.append(
            RecentAttemptResponse(
                student_name=(
                    user["name"]
                    if user
                    else "Deleted Student"
                ),
                quiz_title=(
                    quiz["title"]
                    if quiz
                    else "Deleted Quiz"
                ),
                score=result["score"],
                submitted_at=result["submitted_at"],
            )
        )

    logger.info(
        "Admin dashboard statistics fetched successfully."
    )

    return AdminDashboardResponse(
        total_categories=get_total_categories(),
        total_quizzes=get_total_quizzes(),
        total_questions=get_total_questions(),
        total_students=get_total_students(),
        total_attempts=get_total_attempts(),
        recent_attempts=recent_attempts,
    )


def get_student_dashboard(
    student_id: str,
):
    """
    Retrieve dashboard statistics for a student
    """

    logger.info(
        "Fetching student dashboard. Student ID: %s",
        student_id,
    )

    total_categories = get_total_categories()

    available_quizzes = get_total_quizzes()

    quizzes_attempted = get_student_attempt_count(
        student_id,
    )

    average_score = get_student_average_score(
        student_id,
    )

    recent_results = []

    results = list(
        get_student_recent_results(
            student_id,
        )
    )

    for result in results:

        quiz = get_quiz_by_id(
            result["quiz_id"],
        )

        category = get_category_by_id(
            quiz["category_id"],
        )

        recent_results.append(
            StudentRecentResultResponse(
                quiz_title=quiz["title"],
                category_name=category["name"],
                percentage=result["percentage"],
                result_status=result["result_status"],
            )
        )

    logger.info(
        "Student dashboard fetched successfully."
    )

    return StudentDashboardResponse(
        total_categories=total_categories,
        available_quizzes=available_quizzes,
        quizzes_attempted=quizzes_attempted,
        average_score=average_score,
        recent_results=recent_results,
    )