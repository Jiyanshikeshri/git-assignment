from fastapi import FastAPI
from app.config.database import db
from app.routers.auth_router import router as auth_router

app = FastAPI(
    title="Assessment Portal API",
    version="1.0.0"
)

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
        return {
            "status": "error",
            "message": str(e)
        }