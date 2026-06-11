"""
Find the largest of three numbers.
"""


def find_largest_number():
    """
    Find and display the largest among three numbers.
    """
    first_number = float(input("Enter first number: "))
    second_number = float(input("Enter second number: "))
    third_number = float(input("Enter third number: "))

    # Comparing each number against the other two to identify the largest value.
    if first_number >= second_number and first_number >= third_number:
        print("Largest Number =", first_number)
    elif second_number >= first_number and second_number >= third_number:
        print("Largest Number =", second_number)
    else:
        print("Largest Number =", third_number)


if __name__ == "__main__":
    find_largest_number()