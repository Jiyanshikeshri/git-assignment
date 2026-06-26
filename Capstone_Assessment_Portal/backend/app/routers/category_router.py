from fastapi import APIRouter, Depends, status

from app.middleware.auth_middleware import (
    require_admin,
    get_current_user,
)
from app.schemas.category_schema import CategoryCreate
from app.services.category_service import (
    create_new_category,
    fetch_all_categories,
)

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


@router.get("/")
def get_categories(
    current_user=Depends(get_current_user)
):
    """
    Retrieve all categories
    """
    return fetch_all_categories()