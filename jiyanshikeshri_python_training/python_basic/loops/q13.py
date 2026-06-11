"""
Print multiplication table of a number.
"""


def print_multiplication_table():
    """
    Print multiplication table of the entered number
    """
    number = int(input("Enter a number: "))

    for multiplier in range(1, 11):
        print(number, "x", multiplier, "=", number * multiplier)


if __name__ == "__main__":
    print_multiplication_table()