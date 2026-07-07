from enum import Enum

from pydantic import (
    BaseModel,
    Field,
    field_validator,
    model_validator,
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
    
    @model_validator(mode="after")
    def validate_question(self):
        """
        Validate question based on question type
        """

        if self.question_type == QuestionType.MCQ:

            if not self.options or len(self.options) != 4:
                raise ValueError(
                    "MCQ questions must have exactly 4 options."
                )

            if self.correct_answer not in self.options:
                raise ValueError(
                    "Correct answer must be one of the provided options."
                )

        elif self.question_type == QuestionType.TRUE_FALSE:

            expected_options = ["True", "False"]

            if self.options != expected_options:
                raise ValueError(
                    'TRUE_FALSE questions must have options ["True", "False"].'
                )

            if self.correct_answer not in expected_options:
                raise ValueError(
                    'Correct answer must be either "True" or "False".'
                )

        return self
    

class QuestionResponseStudent(BaseModel):
    """
    Schema returned while fetching questions for students
    """

    id: str
    quiz_id: str
    question_text: str
    question_type: str
    options: list[str] | None
    difficulty: str
    tags: list[str]


class QuestionResponseAdmin(BaseModel):
    """
    Schema returned while fetching questions for admins
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
    
    @model_validator(mode="after")
    def validate_question(self):
        """
        Validate question based on question type
        """

        if self.question_type == QuestionType.MCQ:

            if not self.options or len(self.options) != 4:
                raise ValueError(
                    "MCQ questions must have exactly 4 options."
                )

            if self.correct_answer not in self.options:
                raise ValueError(
                    "Correct answer must be one of the provided options."
                )

        elif self.question_type == QuestionType.TRUE_FALSE:

            expected_options = ["True", "False"]

            if self.options != expected_options:
                raise ValueError(
                    'TRUE_FALSE questions must have options ["True", "False"].'
                )

            if self.correct_answer not in expected_options:
                raise ValueError(
                    'Correct answer must be either "True" or "False".'
                )

        return self