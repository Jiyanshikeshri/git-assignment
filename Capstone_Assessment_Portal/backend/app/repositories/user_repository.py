from bson import ObjectId

from app.config.database import db

# Reference to the users collection in MongoDB
users_collection = db["users"]


def get_user_by_email(email: str):
    """
    Fetch a user from the database using their email address
    Returns None if no matching user is found
    """
    user = users_collection.find_one({"email": email})
    return user

def get_user_by_username(username: str):
    """
    Fetch a user by username
    Returns None if no matching user exists
    """
    user = users_collection.find_one({"username": username})
    return user


def create_user(user_data: dict):
    """
    Insert a new user into the users collection
    """
    result = users_collection.insert_one(user_data)
    return result

def get_user_by_id(user_id: str):
    """
    Fetch a user using the ObjectId stored as _id.
    """
    user =  users_collection.find_one(
        {"_id": ObjectId(user_id)}
    )
    return user
