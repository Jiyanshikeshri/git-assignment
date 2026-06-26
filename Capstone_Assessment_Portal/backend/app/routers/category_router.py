from fastapi import APIRouter, Depends, status

from app.middleware.auth_middleware import require_admin
from app.schemas.category_schema import CategoryCreate
from app.services.category_service import create_new_category

router = APIRouter(
    prefix="/categories",
    tags=["Category Management"]
)


@router.post("/", status_code=status.HTTP_201_CREATED)
def create_category(
    category: CategoryCreate,
    current_user=Depends(require_admin)
):
    """
    Creates a new category
    """
    return create_new_category(category)