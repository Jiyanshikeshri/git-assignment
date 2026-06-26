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


def get_all_categories():
    """
    Retrieve all categories from the database
    """
    return db.categories.find().sort("name", 1)


from bson import ObjectId


def update_category(category_id: str, updated_data: dict):
    """
    Update an existing category by its ID
    """
    return db.categories.update_one(
        {"_id": ObjectId(category_id)},
        {"$set": updated_data}
    )


def get_category_by_name_except_id(name: str, category_id: str):
    """
    Retrieve a category with the given name excluding the current category being updated

    This prevents renaming one category to the name of another existing category
    """
    return db.categories.find_one({
        "name": name,
        "_id": {"$ne": ObjectId(category_id)}
    })