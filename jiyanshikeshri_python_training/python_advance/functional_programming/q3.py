"""
Program to extract even numbers from a list using filter().
"""

def display_even_numbers():
    """
    Filter even numbers from a list.
    """
    numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

    # filter() keeps only the elements that satisfy the given condition
    even_numbers = list(filter(lambda number: number % 2 == 0, numbers))

    print("Original List:", numbers)
    print("Even Numbers:", even_numbers)


if __name__ == "__main__":
    display_even_numbers()