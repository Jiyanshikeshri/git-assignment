"""
Program to convert a loop-based square calculation into a
functional style using map() or filter().
"""

def convert_to_squares():
    """
    Uses map() to calculate squares of numbers
    """
    numbers = [1, 2, 3, 4, 5]

    # map() applies the lambda function to every element without writing an explicit loop.
    squares= list(map(lambda number: number * number, numbers))

    print("Original List:", numbers)
    print("Squared List:", squares)


if __name__ == "__main__":
    convert_to_squares()