"""
Program to append data to an existing file.
"""

def append_data_to_file():
    """
    Append new data to an existing file.
    """
    with open("jiyanshi.txt", "a") as file:
        file.write("\nPython Training")


if __name__ == "__main__":
    append_data_to_file()