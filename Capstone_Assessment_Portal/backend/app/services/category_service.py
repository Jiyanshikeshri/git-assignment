from fastapi import HTTPException, status

from app.repositories.category_repository import (
    get_category_by_name,
    create_category,
    get_all_categories,
    get_category_by_id,
    get_category_by_name_except_id,
    update_category,
    delete_category,
)
from app.schemas.category_schema import (
    CategoryCreate,
    CategoryUpdate,
)


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


def fetch_all_categories():
    """
    Retrieve all categories
    """

    categories = []

    for category in get_all_categories():
        categories.append(
            {
                "id": str(category["_id"]),
                "name": category["name"],
            }
        )

    return categories


def update_existing_category(
    category_id: str,
    category: CategoryUpdate,
):
    """
    Update an existing category after validating: Category exists and new name is unique
    """

    category_name = category.name.strip().lower()

    existing_category = get_category_by_id(category_id)

    if not existing_category:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Category not found."
        )

    duplicate_category = get_category_by_name_except_id(
        category_name,
        category_id,
    )

    if duplicate_category:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Category with this name already exists."
        )

    update_category(
        category_id,
        {
            "name": category_name,
        },
    )

    return {
        "message": "Category updated successfully."
    }


def delete_existing_category(category_id: str):
    """
    Delete an existing category
    """

    existing_category = get_category_by_id(category_id)

    if not existing_category:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Category not found."
        )

    delete_category(category_id)

    return {
        "message": "Category deleted successfully."
    }