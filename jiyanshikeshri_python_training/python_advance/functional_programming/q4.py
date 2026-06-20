"""
Program to find the product of all elements in a list using reduce().
"""

from functools import reduce

def calculate_product():
    """
    Calculating the product of list elements using reduce()
    """
    numbers = [1, 2, 3, 4, 5]

    # reduce() repeatedly combines elements into a single output
    product = reduce(lambda first, second: first * second, numbers)

    print("Numbers:", numbers)
    print("Product:", product)


if __name__ == "__main__":
    calculate_product()