"""
Program to demonstrate the use of join() in threading.
"""

import threading
import time


def perform_task():
    """
    performing a task that takes some time
    """
    print("Task started")
    time.sleep(5)
    print("Task completed")


if __name__ == "__main__":
    worker_thread = threading.Thread(target=perform_task)

    worker_thread.start()

    # join() makes the main thread wait until the worker finishes
    worker_thread.join()

    print("Main program continues after thread completion")