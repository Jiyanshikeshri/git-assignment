from fastapi import FastAPI
from app.config.database import db

app = FastAPI(
    title="Assessment Portal API",
    version="1.0.0"
)

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
        return {
            "status": "error",
            "message": str(e)
        }