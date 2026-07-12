from datetime import datetime

from pydantic import BaseModel


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