from passlib.context import CryptContext

# Using bcrypt as the password hashing algorithm
pwd_context = CryptContext(
    schemes=["bcrypt"],
    deprecated="auto"
)

def hash_password(password: str) -> str:
    """
    Hashes a plain text password before storing it in the database
    """
    return pwd_context.hash(password)


def verify_password(plain_password: str, hashed_password: str) -> bool:
    """
    Verifies whether the entered password matches the stored hash
    """
    return pwd_context.verify(plain_password, hashed_password)