from fastapi import APIRouter, Depends, status

from app.schemas.question_schema import (
    QuestionCreate,
)

from app.services.question_service import (
    create_new_question,
)

from app.middleware.auth_middleware import (
    require_admin,
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