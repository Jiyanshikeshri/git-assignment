from datetime import datetime
from enum import Enum

from pydantic import BaseModel


class ResultStatus(
    str,
    Enum,
):
    """
    Represents the final outcome of a quiz attempt
    """

    PASS = "PASS"
    FAIL = "FAIL"


class QuestionResultResponse(
    BaseModel,
):
    """
    Represents the evaluation details of a single question
    """

    question_id: str
    selected_answer: str | None
    correct_answer: str
    is_correct: bool
    score: int


class ResultResponse(
    BaseModel,
):
    """
    Response schema for fetching a student's latest quiz result
    """

    id: str
    attempt_id: str
    quiz_id: str
    student_id: str
    score: int
    correct_answers: int
    total_questions: int
    percentage: float
    result_status: ResultStatus
    submitted_at: datetime
    questions: list[QuestionResultResponse]


class ResultHistoryResponse(
    BaseModel,
):
    """
    Response schema for a student's quiz result history
    """

    id: str
    quiz_id: str
    score: int
    percentage: float
    result_status: ResultStatus
    submitted_at: datetime


class ResultBreakdownResponse(
    BaseModel,
):
    """
    Response schema for detailed quiz result with per-question evaluation
    """

    id: str
    attempt_id: str
    quiz_id: str
    score: int
    correct_answers: int
    total_questions: int
    percentage: float
    result_status: ResultStatus
    submitted_at: datetime
    questions: list[QuestionResultResponse]