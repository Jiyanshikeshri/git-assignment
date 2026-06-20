"""
Program to execute tasks in parallel using ProcessPoolExecutor.
"""

from concurrent.futures import ProcessPoolExecutor

def calculate_square(number):
    """
    Return the square of a number
    """
    return number * number


if __name__ == "__main__":
    numbers = [1, 2, 3, 4, 5]

    # ProcessPoolExecutor distributes work across multiple processes
    with ProcessPoolExecutor() as executor:
        results = executor.map(calculate_square, numbers)

    for square in results:
        print(square)