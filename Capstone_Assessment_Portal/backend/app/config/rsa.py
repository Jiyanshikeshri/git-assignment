"""
Loads RSA public and private keys for password encryption/decryption
"""

from pathlib import Path

from cryptography.hazmat.primitives import serialization

BASE_DIR = Path(__file__).resolve().parent.parent.parent

KEYS_DIRECTORY = BASE_DIR / "keys"

PRIVATE_KEY_PATH = KEYS_DIRECTORY / "private.pem"
PUBLIC_KEY_PATH = KEYS_DIRECTORY / "public.pem"


def load_private_key():
    """
    Load RSA private key from PEM file
    """

    with open(PRIVATE_KEY_PATH, "rb") as private_file:
        return serialization.load_pem_private_key(
            private_file.read(),
            password=None,
        )


def load_public_key():
    """
    Load RSA public key from PEM file
    """

    with open(PUBLIC_KEY_PATH, "rb") as public_file:
        return serialization.load_pem_public_key(
            public_file.read()
        )


PRIVATE_KEY = load_private_key()
PUBLIC_KEY = load_public_key()