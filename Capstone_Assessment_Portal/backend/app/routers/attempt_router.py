from fastapi import (
    APIRouter,
    Depends,
    status,
)

from app.schemas.attempt_schema import (
    StartAttemptRequest,
    StartAttemptResponse,
)

from app.services.attempt_service import (
    start_quiz_attempt,
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