from fastapi import APIRouter, Depends, status

from app.schemas.quiz_schema import (
    QuizCreate,
    QuizUpdate,
)
from app.services.quiz_service import (
    create_new_quiz,
    fetch_all_quizzes,
    fetch_quiz_by_id,
    update_existing_quiz,
    delete_existing_quiz,
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

    response = create_new_quiz(quiz)
    return response


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

    response = fetch_all_quizzes()
    return response


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

    response = fetch_quiz_by_id(
        quiz_id
    )
    return response


@router.put(
    "/{quiz_id}",
    status_code=status.HTTP_200_OK,
)
def update_quiz_details(
    quiz_id: str,
    quiz: QuizUpdate,
    user=Depends(require_admin),
):
    """
    Updates an existing quiz
    """

    response = update_existing_quiz(
        quiz_id,
        quiz,
    )
    return response


@router.delete(
    "/{quiz_id}",
    status_code=status.HTTP_200_OK,
)
def remove_quiz(
    quiz_id: str,
    user=Depends(require_admin),
):
    """
    Delete an existing quiz
    """

    response = delete_existing_quiz(quiz_id)
    return response