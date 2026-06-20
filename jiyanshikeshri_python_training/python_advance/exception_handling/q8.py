"""
Program to handle FileNotFoundError while opening a file.
"""

def open_file():
    """
    Attempting to open a file and handling missing file error
    """
    try:
        file = open("data.txt", "r")
        print(file.read())
        file.close()

    except FileNotFoundError:
        # This runs when file is not found
        print("File not found.")


if __name__ == "__main__":
    open_file()