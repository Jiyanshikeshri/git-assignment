"""
Program to convert a list of numbers into their squares using map().
"""

def display_squared_numbers():
    """
    Applying map() to calculate squares of list elements
    """
    numbers = [1, 2, 3, 4, 5]

    # map() applies the lambda function to every element in the list
    squares = list(map(lambda number: number ** 2, numbers))

    print("Original List:", numbers)
    print("Squared List:", squares)


if __name__ == "__main__":
    display_squared_numbers()