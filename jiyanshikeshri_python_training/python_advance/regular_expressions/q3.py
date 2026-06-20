"""
Program to validate a 10-digit mobile number using regular expressions.
"""

import re

def validate_mobile_number():
    """
    Check whether the entered mobile number contains exactly 10 digits or not
    """
    mobile_number = input("Enter a 10-digit mobile number: ")

    # Pattern matches exactly 10 digits from start to end
    pattern = r"^\d{10}$"

    if re.fullmatch(pattern, mobile_number):
        print("Valid mobile number.")
    else:
        print("Invalid mobile number.")


if __name__ == "__main__":
    validate_mobile_number()