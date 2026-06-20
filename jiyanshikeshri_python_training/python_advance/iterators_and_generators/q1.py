"""
Program to create an iterator for a list and print elements using next().
"""

def iterate_list():
    """
    Creating an iterator from a list and accessing elements using next().
    """
    numbers = [1, 2, 3, 4, 5]
    iterator = iter(numbers)

    print(next(iterator))
    print(next(iterator))
    print(next(iterator))
    print(next(iterator))
    print(next(iterator))


if __name__ == "__main__":
    iterate_list()