"""
Program to read a file and count words, lines, and characters.
"""

def count_file_details():
    """
    Reading a file and displaying the number of words, lines, and characters.
    """
    with open("jiyanshi.txt", "r") as file:
        content = file.read()

    word_count = len(content.split())
    line_count = len(content.splitlines())
    character_count = len(content)

    print("Words:", word_count)
    print("Lines:", line_count)
    print("Characters:", character_count)


if __name__ == "__main__":
    count_file_details()