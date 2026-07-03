from pydantic import BaseModel, Field , field_validator


class QuizCreate(BaseModel):
    """
    Schema used while creating a new quiz
    """

    title: str = Field(
        ...,
        min_length=3,
        max_length=100,
        description="Unique quiz title"
    )

    description: str = Field(
        ...,
        min_length=5,
        max_length=500,
        description="Quiz description"
    )

    category_id: str = Field(
        ...,
        description="Category ID to which the quiz belongs"
    )

    duration: int = Field(
        ...,
        gt=0,
        description="Quiz duration in minutes"
    )


    @field_validator("title", "description")
    @classmethod
    def validate_text_fields(cls, value: str) -> str:
        """
        Ensures text fields are not empty after trimming whitespace
        """
        value = value.strip()

        if not value:
            raise ValueError(
                "Field cannot be empty."
            )

        return value
    

class QuizResponse(BaseModel):
    """
    Schema returned in API responses for quiz data
    """

    id: str
    title: str
    description: str
    category_id: str
    duration: int


class QuizUpdate(BaseModel):
    """
    Schema used while updating an existing quiz
    """

    title: str = Field(
        ...,
        min_length=3,
        max_length=100,
        description="Updated quiz title"
    )

    description: str = Field(
        ...,
        min_length=5,
        max_length=500,
        description="Updated quiz description"
    )

    category_id: str = Field(
        ...,
        description="Updated category ID"
    )

    duration: int = Field(
        ...,
        gt=0,
        description="Updated quiz duration in minutes"
    )


    @field_validator("title", "description")
    @classmethod
    def validate_text_fields(cls, value: str) -> str:
        """
        Ensures text fields are not empty after trimming whitespace
        """
        value = value.strip()

        if not value:
            raise ValueError(
                "Field cannot be empty."
            )

        return value