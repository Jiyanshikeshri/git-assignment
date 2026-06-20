"""
Program to replace multiple spaces with a single space using re.sub().
"""

import re

def replace_multiple_spaces():
    """
    Replace consecutive spaces in a string with a single space
    """
    text = "Python     is    an      interesting    language."

    # \s+ matches one or more whitespace characters
    updated_text = re.sub(r"\s+", " ", text)

    print("Original String:")
    print(text)

    print("\nUpdated String:")
    print(updated_text)


if __name__ == "__main__":
    replace_multiple_spaces()