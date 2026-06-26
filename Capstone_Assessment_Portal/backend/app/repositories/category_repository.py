from bson import ObjectId

from app.config.database import db


def get_category_by_name(name: str):
    """
    Retrieve a category by its name
    """
    return db.categories.find_one({"name": name})


def create_category(category_data: dict):
    """
    Insert a new category into the database
    """
    return db.categories.insert_one(category_data)


def get_category_by_id(category_id: str):
    """
    Retrieve a category using its MongoDB ObjectId
    """
    return db.categories.find_one(
        {"_id": ObjectId(category_id)}
    )