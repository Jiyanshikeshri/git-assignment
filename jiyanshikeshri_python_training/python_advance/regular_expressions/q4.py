"""
Program to check whether a word exists in a sentence using re.search().
"""

import re

def search_word():
    """
    Search for a word inside a sentence
    """
    sentence = "Python programming is fun and interesting."
    word = input("Enter the word to search: ")

    # re.search() returns a match object if the word is found
    if re.search(word, sentence, re.IGNORECASE):
        print(f'"{word}" exists in the sentence.')
    else:
        print(f'"{word}" does not exist in the sentence.')


if __name__ == "__main__":
    search_word()