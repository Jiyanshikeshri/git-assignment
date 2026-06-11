"""
Program to remove duplicates from a list using set.
"""

def remove_duplicates():
    """
    Remove duplicate values from a list.
    """
    numbers = [10, 20, 30, 10, 40, 20, 50]

    unique_numbers = list(set(numbers))

    print("Original List:", numbers)
    print("List Without Duplicates:", unique_numbers)


if __name__ == "__main__":
    remove_duplicates()