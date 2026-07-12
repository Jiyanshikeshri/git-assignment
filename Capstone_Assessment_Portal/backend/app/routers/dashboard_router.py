from fastapi import APIRouter, Depends

from app.middleware.auth_middleware import require_admin

from app.schemas.dashboard_schema import (
    AdminDashboardResponse,
)

from app.services.dashboard_service import (
    get_admin_dashboard,
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

    return get_admin_dashboard()