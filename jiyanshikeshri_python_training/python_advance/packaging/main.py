"""
Program to import and use functions from a custom module.
"""

import q1


def use_utility_functions():
    """
    Call functions defined in the utilities module i.e q1.py
    """
    print(f"Square: {q1.find_square(5)}")
    print(f"Cube: {q1.find_cube(5)}")


if __name__ == "__main__":
    use_utility_functions()