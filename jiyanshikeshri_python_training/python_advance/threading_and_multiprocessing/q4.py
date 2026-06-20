"""
Program to simulate file downloading using multiple threads.
"""

import threading
import time

def download_file(file_name):
    """
    Simulate downloading a file
    """
    print(f"Downloading {file_name}")
    time.sleep(5)
    print(f"{file_name} downloaded successfully")


if __name__ == "__main__":
    thread_one = threading.Thread(target=download_file, args=("file1.pdf",))

    thread_two = threading.Thread(target=download_file, args=("file2.pdf",))

    thread_three = threading.Thread(target=download_file, args=("file3.pdf",))

    thread_one.start()
    thread_two.start()
    thread_three.start()

    thread_one.join()
    thread_two.join()
    thread_three.join()

    print("All downloads completed")