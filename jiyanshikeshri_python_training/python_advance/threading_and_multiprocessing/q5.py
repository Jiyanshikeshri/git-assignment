"""
Program to create two processes and print their Process IDs.
"""

import multiprocessing
import os


def print_process_id():
    """
    Print the Process ID of the current process
    """
    print("Process ID:", os.getpid())


if __name__ == "__main__":
    process_one = multiprocessing.Process(target=print_process_id)
    process_two = multiprocessing.Process(target=print_process_id)

    process_one.start()
    process_two.start()

    process_one.join()
    process_two.join()