"""
Program to execute tasks in parallel using ThreadPoolExecutor.
"""

from concurrent.futures import ThreadPoolExecutor
import time

def print_square(number):
    """
    Calculate and print the square of a number
    """
    time.sleep(2)
    print(f"Square of {number} is {number * number}")


if __name__ == "__main__":
    numbers = [1, 2, 3, 4, 5]

    # ThreadPoolExecutor runs multiple tasks concurrently using threads
    with ThreadPoolExecutor(max_workers=3) as executor:
        executor.map(print_square, numbers)