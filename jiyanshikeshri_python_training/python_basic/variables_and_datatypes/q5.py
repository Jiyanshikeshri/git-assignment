"""
Write a program to swap two numbers.
"""


def swap_numbers():
    first_number = int(input("Enter first number: "))
    second_number = int(input("Enter second number: "))

    print("Before swapping:")
    print("First Number =", first_number)
    print("Second Number =", second_number)

    first_number, second_number = second_number, first_number

    print("After swapping:")
    print("First Number =", first_number)
    print("Second Number =", second_number)


if __name__ == "__main__":
    swap_numbers()