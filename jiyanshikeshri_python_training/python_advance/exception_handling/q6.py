"""
Program to raise a ValueError if the entered number is negative.
"""


def check_positive_number():
    """
    Raise an exception when a negative number is entered.
    """
    number = int(input("Enter a number: "))

    if number < 0:
        raise ValueError("Negative numbers are not allowed.")

    print("Valid number entered.")


if __name__ == "__main__":
    check_positive_number()