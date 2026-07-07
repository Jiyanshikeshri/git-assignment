from fastapi import (
    APIRouter,
    Depends,
    status,
)

from app.middleware.auth_middleware import (
    require_student,
)

from app.schemas.result_schema import (
    ResultResponse,
)

from app.services.result_service import (
    get_latest_student_result,
)

router = APIRouter(
    prefix="/results",
    tags=["Result Management"],
)


@router.get(
    "/latest",
    response_model=ResultResponse,
    status_code=status.HTTP_200_OK,
)
def get_latest_result(
    current_user=Depends(require_student),
):
    """
    Retrieve the latest quiz result of the logged-in student
    """

    return get_latest_student_result(
        current_user["user_id"],
    )