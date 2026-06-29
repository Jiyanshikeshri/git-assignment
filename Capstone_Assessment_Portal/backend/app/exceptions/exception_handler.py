from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse

from app.exceptions.custom_exceptions import (
    AssessmentPortalException,
)


def register_exception_handlers(app: FastAPI):
    """
    Register global exception handlers for the application
    """

    @app.exception_handler(AssessmentPortalException)
    async def assessment_portal_exception_handler(
        request: Request,
        exc: AssessmentPortalException,
    ):
        return JSONResponse(
            status_code=exc.status_code,
            content={
                "detail": exc.detail,
            },
        )