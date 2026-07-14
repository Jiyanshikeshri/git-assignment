"""
Generate RSA Public and Private Keys

Run only once to create the key pair used for
client-side password encryption
"""

from pathlib import Path

from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives.asymmetric import rsa


KEYS_DIRECTORY = Path("keys")
KEYS_DIRECTORY.mkdir(exist_ok=True)


private_key = rsa.generate_private_key(
    public_exponent=65537,
    key_size=2048,
)

public_key = private_key.public_key()


with open(
    KEYS_DIRECTORY / "private.pem",
    "wb",
) as private_file:

    private_file.write(
        private_key.private_bytes(
            encoding=serialization.Encoding.PEM,
            format=serialization.PrivateFormat.PKCS8,
            encryption_algorithm=serialization.NoEncryption(),
        )
    )


with open(
    KEYS_DIRECTORY / "public.pem",
    "wb",
) as public_file:

    public_file.write(
        public_key.public_bytes(
            encoding=serialization.Encoding.PEM,
            format=serialization.PublicFormat.SubjectPublicKeyInfo,
        )
    )

print("RSA key pair generated successfully.")