"""
Program to iterate over a built-in iterable using range().
"""

def iterate_range():
    """
    Iterate through values produced by range()
    """
    for number in range(1, 6):
        print(number)


if __name__ == "__main__":
    iterate_range()