"""
Program to calculate squares of numbers using multiprocessing.
"""

import multiprocessing

def calculate_square(number):
    """
    Calculate and print the square of a number
    """
    print(f"Square of {number} is {number * number}")


if __name__ == "__main__":
    numbers = [2, 4, 6, 8, 10]

    processes = []

    # Creates one process for each number
    for number in numbers:
        process = multiprocessing.Process(target=calculate_square, args=(number,))
        processes.append(process)
        process.start()

    for process in processes:
        process.join()