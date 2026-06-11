"""
Program to calculate square of a number using a function.

SRP Followed:
calculate_square() only calculates square.
display_result() only displays output.
"""


def calculate_square(number):
    """
    Return square of the given number.
    """
    return number * number


def display_result(square):
    """
    Display the calculated square.
    """
    print("Square =", square)

if __name__ == "__main__":
    number = int(input("Enter a number: "))

    square = calculate_square(number)
    display_result(square)