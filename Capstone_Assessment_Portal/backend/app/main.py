from fastapi import FastAPI
from app.config.database import db
from app.routers.auth_router import router as auth_router
from app.exceptions.exception_handler import (
    register_exception_handlers,
)
from app.config.logger import logger

app = FastAPI(
    title="Assessment Portal API",
    version="1.0.0"
)

register_exception_handlers(app)

# Register authentication routes
app.include_router(auth_router)

@app.get("/")
def root():
    return {
        "message": "Assessment Portal API is running successfully!"
    }

@app.get("/test-db")
def test_database():
    try:
        db.command("ping")
        return {
            "status": "success",
            "message": "Connected to MongoDB successfully!"
        }
    except Exception as e:
        logger.error(
            "MongoDB connection test failed: %s",
            e,
        )
        return {
            "status": "error",
            "message": "Unable to connect to the database"
        }