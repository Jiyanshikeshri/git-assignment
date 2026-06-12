"""
Program to create a file and write a name into it.
"""

def write_name_to_file():
    """
    Creating a file and write a name into it.
    """
    with open("jiyanshi.txt", "w") as file:
        file.write("Jiyanshi Keshri")


if __name__ == "__main__":
    write_name_to_file()