"""
Program to count frequency of characters in a string.
"""

def count_character_frequency():
    """
    Count occurrences of each character in a string.
    """
    text = input("Enter a string: ")

    frequency = {}

    for character in text:
        if character in frequency:
            frequency[character] += 1
        else:
            frequency[character] = 1

    print("Character Frequency:", frequency)


if __name__ == "__main__":
    count_character_frequency()