"""
Write a program to divide two numbers entered by the user and handle ZeroDivisionError.
"""

def divide_numbers():
    """
    Take two numbers from the user and perform division.
    """
    try:
        first_number = float(input("Enter first number: "))
        second_number = float(input("Enter second number: "))

        result = first_number / second_number
        print(f"Result: {result}")

    except ZeroDivisionError:
        # Division by zero is not allowed.
        print("Cannot divide by zero.")


if __name__ == "__main__":
    divide_numbers()