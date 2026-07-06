"""
Schemas used for starting a quiz attempt
"""

from datetime import datetime
from enum import Enum
from bson import ObjectId

from pydantic import (
    BaseModel,
    Field,
    field_validator,
)


class AttemptStatus(str, Enum):
    """
    Supported quiz attempt status
    """

    IN_PROGRESS = "IN_PROGRESS"
    SUBMITTED = "SUBMITTED"
    EXPIRED = "EXPIRED"


class StartAttemptRequest(BaseModel):
    """
    Schema used while starting a quiz attempt
    """

    quiz_id: str = Field(
        ...,
        description="Quiz ID for which the attempt is being started.",
    )

    @field_validator("quiz_id")
    @classmethod
    def validate_quiz_id(
        cls,
        value: str,
    ) -> str:
        """
        Validate quiz ID
        """

        value = value.strip()

        if not value:
            raise ValueError(
                "Quiz ID cannot be empty."
            )

        return value


class QuestionSnapshot(BaseModel):
    """
    Schema representing a snapshot of a question stored at the time of starting a quiz attempt.
    """

    question_id: str

    question_text: str

    question_type: str

    options: list[str]

    correct_answer: str

    difficulty: str


class StartAttemptResponse(BaseModel):
    """
    Schema returned after successfully starting a quiz attempt
    """

    attempt_id: str

    quiz_id: str

    status: AttemptStatus

    started_at: datetime

    expires_at: datetime

    total_questions: int


class SaveAnswerRequest(BaseModel):
    """
    Schema used for saving a student's answer for a question
    """

    question_id: str = Field(
        ...,
        description="Question ID from the quiz attempt snapshot.",
    )

    selected_answer: str = Field(
        ...,
        description="Answer selected by the student.",
    )

    @field_validator(
        "question_id",
        "selected_answer",
    )
    @classmethod
    def validate_fields(
        cls,
        value: str,
    ) -> str:
        """
        Validate request fields
        """

        value = value.strip()

        if not value:
            raise ValueError(
                "Field cannot be empty."
            )

        return value
    

class ResumeQuestionResponse(BaseModel):
    """
    Question returned while resuming an attempt
    """

    question_id: str

    question_text: str

    question_type: str

    options: list[str]

    difficulty: str

    selected_answer: str | None = None


class ResumeAttemptResponse(BaseModel):
    """
    Response returned when resuming an existing quiz attempt
    """

    attempt_id: str

    quiz_id: str

    status: AttemptStatus

    started_at: datetime

    expires_at: datetime

    remaining_time: int

    questions: list[ResumeQuestionResponse]