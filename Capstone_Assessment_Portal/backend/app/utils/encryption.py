"""
Utility functions for RSA password encryption and decryption
"""

import base64

from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.asymmetric import padding

from app.config.rsa import PRIVATE_KEY


def decrypt_password(encrypted_password: str) -> str:
    """
    Decrypt an RSA encrypted password received from the frontend
    """

    try:

        encrypted_bytes = base64.b64decode(
            encrypted_password
        )

        decrypted_bytes = PRIVATE_KEY.decrypt(
            encrypted_bytes,
            padding.PKCS1v15()
        )

        return decrypted_bytes.decode("utf-8")

    except Exception as exception:
        raise ValueError(
            "Unable to decrypt password."
        ) from exception