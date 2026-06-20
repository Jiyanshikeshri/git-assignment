"""
Program to find the square of a number using a lambda function.
"""

def calculate_square():
    """
    Calculating and displaying the square of a number using lambda
    """
    square = lambda number: number ** 2

    number = int(input("Enter a number: "))
    print(f"Square: {square(number)}")


if __name__ == "__main__":
    calculate_square()