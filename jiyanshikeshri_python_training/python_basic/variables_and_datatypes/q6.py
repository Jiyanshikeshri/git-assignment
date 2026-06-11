"""
Take two numbers and print sum, difference, multiplication, and division.
"""


def arithmetic_operations():
    """
    Take two numbers as input and perform arithmetic operations."""
    first_number = float(input("Enter first number: "))
    second_number = float(input("Enter second number: "))

    print("Sum =", first_number + second_number)
    print("Difference =", first_number - second_number)
    print("Multiplication =", first_number * second_number)
    print("Division =", first_number / second_number)


if __name__ == "__main__":
    arithmetic_operations()