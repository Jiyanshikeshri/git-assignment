"""
Program to use modules from a custom package.
"""

from my_package.greetings import greet
from my_package.addition import add


def demonstrate_package():
    """
    Demonstrating package usage
    """
    greet()
    print(f"Addition Result: {add(10, 20)}")


if __name__ == "__main__":
    demonstrate_package()