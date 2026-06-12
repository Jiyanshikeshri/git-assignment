"""
Program to copy content from one file to another.
"""

def copy_file_content():
    """
    Copying the contents of one file into another file.
    """
    with open("jiyanshi.txt", "r") as source_file:
        content = source_file.read()

    with open("copy.txt", "w") as destination_file:
        destination_file.write(content)


if __name__ == "__main__":
    copy_file_content()