"""
Program to validate a password using regular expressions.
"""

import re

def validate_password():
    """
    Validate a password based on minimum length,
    at least one digit, and at least one special character
    """
    password = input("Enter a password: ")

    # Pattern requirements is at least 8 characters, at least one digit and at least one special character
    pattern = r"^(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$"

    if re.fullmatch(pattern, password):
        print("Password is valid.")
    else:
        print("Password is invalid.")


if __name__ == "__main__":
    validate_password()