"""
Program to find maximum number from a list.

SRP Followed:
find_maximum() only finds maximum value.
display_result() only displays output.
"""


def find_maximum(numbers):
    """
    Return maximum number from the list.
    """
    return max(numbers)


def display_result(maximum_number):
    """
    Display maximum number.
    """
    print(f"Maximum number is: {maximum_number}")


if __name__ == "__main__":
    numbers = [12, 45, 67, 23, 89, 34]

    maximum_number = find_maximum(numbers)
    display_result(maximum_number)