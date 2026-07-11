from fastapi import APIRouter, Depends, status

from app.schemas.question_schema import (
    QuestionCreate,
    QuestionUpdate,
)

from app.services.question_service import (
    create_new_question,
    fetch_questions_by_quiz,
    update_existing_question,
    delete_existing_question,
    fetch_all_questions,
)

from app.middleware.auth_middleware import (
    require_admin,
    get_current_user,
)

router = APIRouter(
    prefix="/questions",
    tags=["Question"],
)


@router.post(
    "/",
    status_code=status.HTTP_201_CREATED,
)
def create_question(
    question: QuestionCreate,
    user=Depends(require_admin),
):
    """
    Create a new question and only admins are allowed to create questions
    """

    response = create_new_question(
        question
    )

    return response

@router.get(
    "/",
    status_code=status.HTTP_200_OK,
)
def get_all_questions_details(
    user=Depends(require_admin),
):
    """
    Retrieve all questions
    """

    response = fetch_all_questions()

    return response


@router.get(
    "/quiz/{quiz_id}",
    status_code=status.HTTP_200_OK,
)
def get_questions(
    quiz_id: str,
    user=Depends(get_current_user),
):
    """
    Retrieve all questions belonging to a quiz
    """

    response = fetch_questions_by_quiz(
        quiz_id,
        user,
    )

    return response


@router.put(
    "/{question_id}",
    status_code=status.HTTP_200_OK,
)
def update_question_details(
    question_id: str,
    question: QuestionUpdate,
    user=Depends(require_admin),
):
    """
    Update an existing question
    """

    response = update_existing_question(
        question_id,
        question,
    )

    return response


@router.delete(
    "/{question_id}",
    status_code=status.HTTP_200_OK,
)
def remove_question(
    question_id: str,
    user=Depends(require_admin),
):
    """
    Delete an existing question
    """

    response = delete_existing_question(
        question_id
    )

    return response