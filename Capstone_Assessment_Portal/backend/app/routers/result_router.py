from fastapi import (
    APIRouter,
    Depends,
    status,
)

from app.middleware.auth_middleware import (
    require_student,
    require_admin,
)

from app.schemas.result_schema import (
    ResultResponse,
    ResultHistoryResponse,
    AdminResultResponse,
)

from app.services.result_service import (
    get_latest_student_result,
    get_student_result_history,
    get_admin_results,
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


@router.get(
    "/history",
    response_model=list[ResultHistoryResponse],
    status_code=status.HTTP_200_OK,
)
def get_result_history(
    current_user=Depends(require_student),
):
    """
    Retrieve the complete quiz result history of the logged-in student
    """

    return get_student_result_history(
        current_user["user_id"],
    )


@router.get(
    "/",
    response_model=list[AdminResultResponse],
    status_code=status.HTTP_200_OK,
)
def get_results(
    current_user=Depends(require_admin),
):
    """
    Retrieve all quiz results for the admin dashboard
    """

    return get_admin_results()