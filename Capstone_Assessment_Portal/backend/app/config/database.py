from pymongo import MongoClient
from dotenv import load_dotenv
import os

# Loading environment variables from .env
load_dotenv()

# Reading values from .env
MONGODB_URI = os.getenv("MONGODB_URI")
DATABASE_NAME = os.getenv("DATABASE_NAME")

# Creating MongoDB client
client = MongoClient(MONGODB_URI)

# Connecting to the database
db = client[DATABASE_NAME]