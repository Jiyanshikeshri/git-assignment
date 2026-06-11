"""
Check whether a number is positive, negative, or zero.
"""


def check_number_type():
    """
    Check whether the entered number is positive, negative, or zero.
    """
    number = float(input("Enter a number: "))

    #if a number is greater than 0, it is positive; if it is less than 0, it is negative; otherwise, it is zero.
    if number > 0:
        print("Positive Number")
    elif number < 0:
        print("Negative Number")
    else:
        print("Zero")


if __name__ == "__main__":
    check_number_type()