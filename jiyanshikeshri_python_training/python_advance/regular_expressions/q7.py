"""
Program to check whether a string contains only alphabets.
"""

import re

def validate_alphabets():
    """
    Check whether the entered string contains only alphabetic characters
    """
    text = input("Enter a string: ")

    # The pattern allows only uppercase and lowercase English letters
    pattern = r"^[A-Za-z]+$"

    if re.fullmatch(pattern, text):
        print("The string contains only alphabets.")
    else:
        print("The string contains characters other than alphabets.")


if __name__ == "__main__":
    validate_alphabets()