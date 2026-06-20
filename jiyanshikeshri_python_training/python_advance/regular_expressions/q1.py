"""
Program to extract all numbers from a string using regular expressions.
"""

import re

def extract_numbers():
    """
    Extract and display all numbers present in the given string.
    """
    text = "I bought 5 pencils, 2 erasers, and 1 sharpener."

    # \d+ matches one or more consecutive digits.
    numbers = re.findall(r"\d+", text)

    print(f"Numbers found: {numbers}")


if __name__ == "__main__":
    extract_numbers()