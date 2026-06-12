"""
Program to search for a word in a file.
"""

def search_word_in_file():
    """
    Searching whether a given word exists in a file.
    """
    word = input("Enter a word to search: ")

    with open("jiyanshi.txt", "r") as file:
        content = file.read()

    if word.lower() in content.lower():
        print("Word found in the file.")
    else:
        print("Word not found in the file.")


if __name__ == "__main__":
    search_word_in_file()