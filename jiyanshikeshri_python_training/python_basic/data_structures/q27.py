"""
Program to reverse a list without using reverse().
"""

def reverse_list():
    """
    Reversing a list using slicing.
    """
    numbers = [10, 20, 30, 40, 50]

    reversed_numbers = numbers[::-1]

    print("Original List:", numbers)
    print("Reversed List:", reversed_numbers)


if __name__ == "__main__":
    reverse_list()