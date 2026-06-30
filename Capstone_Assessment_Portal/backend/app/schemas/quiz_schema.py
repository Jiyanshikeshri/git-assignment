from pydantic import BaseModel, Field


class QuizCreate(BaseModel):
    """
    Schema used while creating a new quiz
    """

    title: str = Field(
        ...,
        min_length=3,
        max_length=100,
    )

    description: str = Field(
        ...,
        min_length=5,
        max_length=500,
    )

    category_id: str

    duration: int = Field(
        ...,
        gt=0,
    )


class QuizUpdate(BaseModel):
    """
    Schema used while updating an existing quiz
    """

    title: str = Field(
        ...,
        min_length=3,
        max_length=100,
    )

    description: str = Field(
        ...,
        min_length=5,
        max_length=500,
    )

    category_id: str

    duration: int = Field(
        ...,
        gt=0,
    )