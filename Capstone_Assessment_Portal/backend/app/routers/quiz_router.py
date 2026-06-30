from fastapi import APIRouter, Depends, status

from app.schemas.quiz_schema import QuizCreate
from app.services.quiz_service import (
    create_new_quiz,
    fetch_all_quizzes,
    fetch_quiz_by_id,
)
from app.middleware.auth_middleware import (
    require_admin,
    get_current_user,
)

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


@router.get(
    "/",
    status_code=status.HTTP_200_OK,
)
def get_all_quizzes(
    user=Depends(get_current_user),
):
    """
    Retrieve all quizzes
    """

    return fetch_all_quizzes()


@router.get(
    "/{quiz_id}",
    status_code=status.HTTP_200_OK,
)
def get_quiz(
    quiz_id: str,
    user=Depends(get_current_user),
):
    """
    Retrieve a quiz by its ID
    """

    return fetch_quiz_by_id(
        quiz_id
    )