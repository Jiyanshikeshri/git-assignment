from fastapi import APIRouter, Depends, status

from app.middleware.auth_middleware import (
    require_admin,
    get_current_user,
)
from app.schemas.category_schema import (
    CategoryCreate,
    CategoryUpdate,
)
from app.services.category_service import (
    create_new_category,
    fetch_all_categories,
    update_existing_category,
    delete_existing_category,
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
    response = create_new_category(category)
    return response


@router.get("/")
def get_categories(
    current_user=Depends(get_current_user)
):
    """
    Retrieve all categories
    """
    response = fetch_all_categories()
    return response


@router.put("/{category_id}", status_code=status.HTTP_200_OK)
def update_category(
    category_id: str,
    category: CategoryUpdate,
    current_user=Depends(require_admin),
):
    """
    Update an existing category
    """
    response = update_existing_category(
        category_id=category_id,
        category=category,
    )
    return response


@router.delete("/{category_id}", status_code=status.HTTP_200_OK)
def delete_category(
    category_id: str,
    current_user=Depends(require_admin),
):
    """
    Delete an existing category
    """
    response = delete_existing_category(category_id)
    return response