from fastapi import HTTPException, status

from app.repositories.category_repository import (
    get_category_by_name,
    create_category,
)
from app.schemas.category_schema import CategoryCreate


def create_new_category(category: CategoryCreate):
    """
    Creates a new category after validating that the category name does not already exist
    """

    # Remove leading/trailing whitespace
    category_name = category.name.strip().lower()

    # Check for duplicate category name
    existing_category = get_category_by_name(category_name)

    if existing_category:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Category with this name already exists."
        )

    category_data = {
        "name": category_name
    }

    create_category(category_data)

    return {
        "message": "Category created successfully."
    }