from enum import Enum

from pydantic import (
    BaseModel,
    Field,
    field_validator,
)


class QuestionType(str, Enum):
    """
    Supported question types
    """

    MCQ = "MCQ"
    TRUE_FALSE = "TRUE_FALSE"


class DifficultyLevel(str, Enum):
    """
    Supported difficulty levels
    """

    EASY = "EASY"
    MEDIUM = "MEDIUM"
    HARD = "HARD"


class QuestionCreate(BaseModel):
    """
    Schema used while creating a question
    """

    quiz_id: str

    question_text: str = Field(
        ...,
        min_length=5,
        max_length=500,
    )

    question_type: QuestionType

    options: list[str] | None = None

    correct_answer: str

    difficulty: DifficultyLevel

    tags: list[str] = []

    @field_validator("question_text")
    @classmethod
    def validate_question_text(cls, value: str) -> str:
        value = value.strip()

        if not value:
            raise ValueError(
                "Question text cannot be empty."
            )

        return value
    

class QuestionResponse(BaseModel):
    """
    Schema returned while fetching questions
    """

    id: str
    quiz_id: str
    question_text: str
    question_type: str
    options: list[str] | None
    correct_answer: str
    difficulty: str
    tags: list[str]


class QuestionUpdate(BaseModel):
    """
    Schema used while updating a question
    """

    quiz_id: str

    question_text: str = Field(
        ...,
        min_length=5,
        max_length=500,
    )

    question_type: QuestionType

    options: list[str] | None = None

    correct_answer: str

    difficulty: DifficultyLevel

    tags: list[str] = []

    @field_validator("question_text")
    @classmethod
    def validate_question_text(cls, value: str) -> str:
        value = value.strip()

        if not value:
            raise ValueError(
                "Question text cannot be empty."
            )

        return value