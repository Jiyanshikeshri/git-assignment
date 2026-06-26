from pydantic import BaseModel, Field, field_validator


class CategoryCreate(BaseModel):
    """
    Schema used to create a new category
    """

    name: str = Field(
        ...,
        min_length=2,
        max_length=50,
        description="Unique category name"
    )

    @field_validator("name")
    @classmethod
    def validate_name(cls, value: str) -> str:
        """
        Ensures that the category name is not empty after removing leading/trailing spaces
        """
        value = value.strip()

        if not value:
            raise ValueError("Category name cannot be empty.")

        return value


class CategoryResponse(BaseModel):
    """
    Schema returned in API responses for category data
    """

    id: str
    name: str