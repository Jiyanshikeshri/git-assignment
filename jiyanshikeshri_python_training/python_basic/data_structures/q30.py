"""
Program to perform set operations.
"""

def perform_set_operations():
    """
    Display union, intersection, and difference of two sets.
    """
    set_one = {1, 2, 3, 4, 5}
    set_two = {4, 5, 6, 7, 8}

    print("Union:", set_one.union(set_two))
    print("Intersection:", set_one.intersection(set_two))
    print("Difference:", set_one.difference(set_two))


if __name__ == "__main__":
    perform_set_operations()