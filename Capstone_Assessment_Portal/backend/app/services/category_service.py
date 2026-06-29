from app.constants.constants import (
    CATEGORY_ALREADY_EXISTS,
    CATEGORY_CREATED_SUCCESSFULLY,
    CATEGORY_UPDATED_SUCCESSFULLY,
    CATEGORY_DELETED_SUCCESSFULLY,
    CATEGORY_NOT_FOUND,
)

from app.exceptions.custom_exceptions import(
    BadRequestException,
    NotFoundException,
)

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
        raise BadRequestException(
            CATEGORY_ALREADY_EXISTS
        )

    category_data = {
        "name": category_name
    }

    create_category(category_data)

    return {
        "message": CATEGORY_CREATED_SUCCESSFULLY
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
        raise NotFoundException(
            CATEGORY_NOT_FOUND
        )

    duplicate_category = get_category_by_name_except_id(
        category_name,
        category_id,
    )

    if duplicate_category:
        raise BadRequestException(
            CATEGORY_ALREADY_EXISTS
        )

    update_category(
        category_id,
        {
            "name": category_name,
        },
    )

    return {
        "message": CATEGORY_UPDATED_SUCCESSFULLY
    }


def delete_existing_category(category_id: str):
    """
    Delete an existing category
    """

    existing_category = get_category_by_id(category_id)

    if not existing_category:
        raise NotFoundException(
            CATEGORY_NOT_FOUND
        )

    delete_category(category_id)

    return {
        "message": CATEGORY_DELETED_SUCCESSFULLY
    }