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
)

from app.repositories.user_repository import (
    get_user_by_id,
)

from app.repositories.quiz_repository import (
    get_quiz_by_id,
)

from app.schemas.dashboard_schema import (
    AdminDashboardResponse,
    RecentAttemptResponse,
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