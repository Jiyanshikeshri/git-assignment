import base64

from cryptography.hazmat.primitives.asymmetric import padding

from app.config.rsa import PUBLIC_KEY


def encrypt_password(password: str):

    encrypted = PUBLIC_KEY.encrypt(
        password.encode("utf-8"),
        padding.PKCS1v15()
    )

    return base64.b64encode(
        encrypted
    ).decode("utf-8")