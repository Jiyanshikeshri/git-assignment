from fastapi import (
    APIRouter,
    Depends,
    status,
    Path,
)

from app.schemas.attempt_schema import (
    StartAttemptRequest,
    StartAttemptResponse,
    SaveAnswerRequest,
    ResumeAttemptResponse,
    SubmitAttemptResponse,
)

from app.schemas.common_schema import (
    MessageResponse,
)

from app.services.attempt_service import (
    start_quiz_attempt,
    save_partial_answer,
    resume_quiz_attempt,
    submit_quiz_attempt,
)

from app.middleware.auth_middleware import (
    require_student,
)

router = APIRouter(
    prefix="/attempts",
    tags=["Quiz Attempt"],
)


@router.post(
    "/start",
    response_model=StartAttemptResponse,
    status_code=status.HTTP_201_CREATED,
)
def start_attempt(
    attempt: StartAttemptRequest,
    current_user=Depends(require_student),
):
    """
    Start a new quiz attempt
    """

    return start_quiz_attempt(
        attempt,
        current_user["user_id"],
    )


@router.patch(
    "/{attempt_id}/answer",
    response_model=MessageResponse,
    status_code=status.HTTP_200_OK,
)
def save_answer(
    attempt_id: str = Path(
        ...,
        description="Quiz attempt ID",
    ),
    answer: SaveAnswerRequest = ...,
    current_user=Depends(require_student),
):
    """
    Save or update a student's answer during an ongoing quiz attempt
    """

    return save_partial_answer(
        attempt_id=attempt_id,
        answer=answer,
        student_id=current_user["user_id"],
    )


@router.get(
    "/{attempt_id}",
    response_model=ResumeAttemptResponse,
    status_code=status.HTTP_200_OK,
)
def resume_attempt(
    attempt_id: str = Path(
        ...,
        description="Quiz attempt ID",
    ),
    user=Depends(require_student),
):
    """
    Resume an existing quiz attempt
    """

    return resume_quiz_attempt(
        attempt_id=attempt_id,
        student_id=user["user_id"],
    )


@router.patch(
    "/{attempt_id}/submit",
    response_model=SubmitAttemptResponse,
    status_code=status.HTTP_200_OK,
)
def submit_attempt(
    attempt_id: str = Path(
        ...,
        description="Quiz attempt ID",
    ),
    user=Depends(require_student),
):
    """
    Submit a quiz attempt
    """

    return submit_quiz_attempt(
        attempt_id=attempt_id,
        student_id=user["user_id"],
    )