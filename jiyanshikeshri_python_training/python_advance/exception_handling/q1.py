"""
Write a program that takes a number as input and handles ValueError if the input is not a valid integer.
"""

def validate_integer_input():
    """
    Accept an integer from the user and handle invalid input.
    """
    try:
        number = int(input("Enter an integer: "))
        print(f"You entered: {number}")
    except ValueError:
        # This block will execute when conversion to integer fails.
        print("Invalid input. Please enter a valid integer.")


if __name__ == "__main__":
    validate_integer_input()