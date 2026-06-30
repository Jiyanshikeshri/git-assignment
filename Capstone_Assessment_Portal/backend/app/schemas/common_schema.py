from pydantic import BaseModel


class MessageResponse(BaseModel):
    """
    Common response schema used for success messages
    """

    message: str