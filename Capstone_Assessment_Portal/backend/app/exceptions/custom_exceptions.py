from fastapi import status


class AssessmentPortalException(Exception):
    """
    Base exception class for all custom exceptions in the application.
    """

    def __init__(self, status_code: int, detail: str):
        self.status_code = status_code
        self.detail = detail


class BadRequestException(AssessmentPortalException):
    """
    Raised when the request is invalid
    """

    def __init__(self, detail: str):
        super().__init__(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=detail,
        )


class UnauthorizedException(AssessmentPortalException):
    """
    Raised when authentication fails
    """

    def __init__(self, detail: str):
        super().__init__(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail=detail,
        )


class ForbiddenException(AssessmentPortalException):
    """
    Raised when the user does not have permission
    """

    def __init__(self, detail: str):
        super().__init__(
            status_code=status.HTTP_403_FORBIDDEN,
            detail=detail,
        )


class NotFoundException(AssessmentPortalException):
    """
    Raised when a requested resource is not found
    """

    def __init__(self, detail: str):
        super().__init__(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=detail,
        )