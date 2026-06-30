from fastapi import APIRouter, Depends, status

from app.schemas.quiz_schema import QuizCreate
from app.services.quiz_service import create_new_quiz
from app.middleware.auth_middleware import require_admin

router = APIRouter(
    prefix="/quizzes",
    tags=["Quiz"],
)


@router.post(
    "/",
    status_code=status.HTTP_201_CREATED,
)
def create_quiz(
    quiz: QuizCreate,
    user=Depends(require_admin),
):
    """
    Create a new quiz
    Only admins are allowed to create quizzes
    """

    return create_new_quiz(quiz)