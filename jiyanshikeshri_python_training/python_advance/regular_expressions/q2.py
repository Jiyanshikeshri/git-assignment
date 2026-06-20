"""
Program to validate an email address using regular expressions.
"""

import re

def validate_email():
    """
    Check whether the entered email address is valid or not 
    """
    email = input("Enter an email address: ")

    # Pattern checks for a basic email format
    pattern = r"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"

    if re.fullmatch(pattern, email):
        print("Valid email address.")
    else:
        print("Invalid email address.")


if __name__ == "__main__":
    validate_email()