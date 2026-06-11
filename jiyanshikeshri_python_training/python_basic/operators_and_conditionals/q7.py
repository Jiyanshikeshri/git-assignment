"""
Write a program to check whether a number is even or odd.
"""


def check_even_or_odd():
    """
    Check whether the entered number is even or odd.
    """
    number = int(input("Enter a number: "))

    # An even number always leaves remainder 0 when divided by 2.
    if number % 2 == 0:
        print("Even Number")
    else:
        print("Odd Number")


if __name__ == "__main__":
    check_even_or_odd()