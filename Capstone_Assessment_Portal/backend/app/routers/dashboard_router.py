from fastapi import APIRouter, Depends

from app.middleware.auth_middleware import (
    require_admin,
    require_student,
)

from app.schemas.dashboard_schema import (
    AdminDashboardResponse,
)

from app.services.dashboard_service import (
    get_admin_dashboard,
    get_student_dashboard,
)

router = APIRouter(
    prefix="/dashboard",
    tags=["Dashboard"],
)


@router.get(
    "/admin",
    response_model=AdminDashboardResponse,
)
def admin_dashboard(
    current_user=Depends(require_admin),
):
    """
    Retrieve admin dashboard statistics
    """

    response = get_admin_dashboard()
    return response


@router.get(
    "/student",
)
def student_dashboard(
    current_user: dict = Depends(require_student),
):
    """
    Retrieve dashboard statistics for the logged-in student
    """

    response = get_student_dashboard(
        current_user["user_id"],
    )
    return response