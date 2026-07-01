from app.constants.constants import (
    CATEGORY_ALREADY_EXISTS,
    CATEGORY_CREATED_SUCCESSFULLY,
    CATEGORY_UPDATED_SUCCESSFULLY,
    CATEGORY_DELETED_SUCCESSFULLY,
    CATEGORY_NOT_FOUND,
)

from app.exceptions.custom_exceptions import (
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

from app.config.logger import logger

from app.schemas.common_schema import MessageResponse


def create_new_category(category: CategoryCreate):
    """
    Creates a new category after validating that the category name does not already exist
    """

    logger.info(
        "Category creation request received. Name: %s",
        category.name,
    )

    category_name = category.name.strip().lower()

    existing_category = get_category_by_name(category_name)

    if existing_category:
        logger.warning(
            "Category creation failed. Category already exists: %s",
            category_name,
        )
        raise BadRequestException(
            CATEGORY_ALREADY_EXISTS
        )

    category_data = {
        "name": category_name
    }

    create_category(category_data)

    logger.info(
        "Category created successfully: %s",
        category_name,
    )

    response = MessageResponse(
        message=CATEGORY_CREATED_SUCCESSFULLY
    )
    return response


def fetch_all_categories():
    """
    Retrieve all categories
    """

    logger.info(
        "Fetching all categories."
    )

    categories = [
        {
            "id": str(category["_id"]),
            "name": category["name"],
        }
        for category in get_all_categories()
    ]

    logger.info(
        "Retrieved %d categories.",
        len(categories),
    )

    return categories


def update_existing_category(
    category_id: str,
    category: CategoryUpdate,
):
    """
    Update an existing category after validating: Category exists and new name is unique
    """

    logger.info(
        "Category update request received. ID: %s",
        category_id,
    )

    category_name = category.name.strip().lower()

    existing_category = get_category_by_id(category_id)

    if not existing_category:
        logger.warning(
            "Category update failed. Category not found: %s",
            category_id,
        )
        raise NotFoundException(
            CATEGORY_NOT_FOUND
        )

    duplicate_category = get_category_by_name_except_id(
        category_name,
        category_id,
    )

    if duplicate_category:
        logger.warning(
            "Category update failed. Category already exists: %s",
            category_name,
        )
        raise BadRequestException(
            CATEGORY_ALREADY_EXISTS
        )

    update_category(
        category_id,
        {
            "name": category_name,
        },
    )

    logger.info(
        "Category updated successfully. ID: %s",
        category_id,
    )

    response = MessageResponse(
        message=CATEGORY_UPDATED_SUCCESSFULLY
    )
    return response


def delete_existing_category(category_id: str):
    """
    Delete an existing category
    """

    logger.info(
        "Category delete request received. ID: %s",
        category_id,
    )

    existing_category = get_category_by_id(category_id)

    if not existing_category:
        logger.warning(
            "Category deletion failed. Category not found: %s",
            category_id,
        )
        raise NotFoundException(
            CATEGORY_NOT_FOUND
        )

    delete_category(category_id)

    logger.info(
        "Category deleted successfully. ID: %s",
        category_id,
    )

    response = MessageResponse(
        message=CATEGORY_DELETED_SUCCESSFULLY
    )
    return response