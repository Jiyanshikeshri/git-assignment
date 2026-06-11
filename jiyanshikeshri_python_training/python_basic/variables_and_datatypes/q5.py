"""
Write a program to swap two numbers.
"""


def swap_numbers():
    """
    Swap the values of two numbers and display the results.
    """
    first_number = int(input("Enter first number: "))
    second_number = int(input("Enter second number: "))

    print("Before swapping:")
    print("First Number =", first_number)
    print("Second Number =", second_number)

    # Swapping the values of first_number and second_number by placing first number on second number and second number on first number.
    first_number, second_number = second_number, first_number

    print("After swapping:")
    print("First Number =", first_number)
    print("Second Number =", second_number)


if __name__ == "__main__":
    swap_numbers()