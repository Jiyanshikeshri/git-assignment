"""
Program to calculate the sum of numbers from 1 to 100 using a thread.
"""

import threading


def calculate_sum():
    """
    Calculate and print the sum from 1 to 100
    """
    total = sum(range(1, 101))
    print("Sum:", total)


if __name__ == "__main__":
    sum_thread = threading.Thread(target=calculate_sum)

    sum_thread.start()
    sum_thread.join()