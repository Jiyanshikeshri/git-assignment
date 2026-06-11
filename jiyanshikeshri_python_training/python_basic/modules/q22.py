"""
Program to find square root, power, and factorial using math module.
"""

import math


def perform_math_operations():
    """
    Display square root, power, and factorial.
    """
    number = int(input("Enter a number: "))

    print("Square Root:", math.sqrt(number))
    print("Power:", math.pow(number, 2))
    print("Factorial:", math.factorial(number))


if __name__ == "__main__":
    perform_math_operations()