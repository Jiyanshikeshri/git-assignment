from datetime import datetime

from pydantic import BaseModel
from app.schemas.result_schema import ResultStatus

class RecentAttemptResponse(
    BaseModel,
):
    """
    Recent quiz attempts shown on admin dashboard
    """

    student_name: str
    quiz_title: str
    score: int
    submitted_at: datetime


class AdminDashboardResponse(
    BaseModel,
):
    """
    Admin dashboard response
    """

    total_categories: int
    total_quizzes: int
    total_questions: int
    total_students: int
    total_attempts: int
    recent_attempts: list[RecentAttemptResponse]


class StudentRecentResultResponse(BaseModel):
    """
    Response schema for recent student quiz results
    """

    quiz_title: str
    category_name: str
    percentage: float
    result_status: ResultStatus


class StudentDashboardResponse(BaseModel):
    """
    Response schema for student dashboard statistics
    """

    total_categories: int
    available_quizzes: int
    quizzes_attempted: int
    average_score: float
    recent_results: list[StudentRecentResultResponse]