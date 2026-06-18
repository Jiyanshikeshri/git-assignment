"""
Write a program using try-except-else-finally to read a number from a file and print its square.
"""

def read_number_from_file():
    """
    Read a number from a file and display its square.
    """
    try:
        file = open("number.txt", "r")
        number = int(file.read())

    except FileNotFoundError:
        print("The file does not exist.")

    else:
        # else executes only when no exception occurs.
        print(f"Square: {number ** 2}")

    finally:
        # Closing the file only if it was opened successfully.
        try:
            file.close()
        except NameError:
            pass

        print("Program execution completed.")

if __name__ == "__main__":
    read_number_from_file()