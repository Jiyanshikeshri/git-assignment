"""
Program to use a custom mathematical package.
"""

from math_operations.addition import add
from math_operations.subtraction import subtract
from math_operations.multiplication import multiply
from math_operations.division import divide


def perform_operations():
    """
    Demonstrating mathematical operations from the math_operations package
    """
    print(f"Addition: {add(2, 1)}")
    print(f"Subtraction: {subtract(2, 1)}")
    print(f"Multiplication: {multiply(2, 1)}")
    print(f"Division: {divide(2, 1)}")


if __name__ == "__main__":
    perform_operations()