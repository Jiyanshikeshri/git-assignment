"""
Program to check whether a number or string is palindrome.

SRP Followed:
is_palindrome() only checks palindrome logic.
display_result() only displays result.
"""


def is_palindrome(value):
    """
    Return True if input value is palindrome, otherwise False.
    """
    value = str(value)

    # Reverse the value and compare with original
    return value == value[::-1]


def display_result(value):
    """
    Display palindrome result.
    """
    if is_palindrome(value):
        print(value, "is a palindrome.")
    else:
        print(value,"is not a palindrome.")


if __name__ == "__main__":
    user_input = input("Enter a number or string: ")

    display_result(user_input)