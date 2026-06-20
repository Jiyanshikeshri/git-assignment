"""
Program to extract all words starting with a capital letter using re.findall().
"""

import re

def extract_capital_words():
    """
    Extract and display all words that start with a capital letter
    """
    sentence = "Jiyanshi and Avni visited Grandparents house during Summer Vacation."

    # The pattern matches words that begin with an uppercase letter
    capital_words = re.findall(r"\b[A-Z][a-zA-Z]*\b", sentence)

    print("Words starting with a capital letter:")
    print(capital_words)


if __name__ == "__main__":
    extract_capital_words()