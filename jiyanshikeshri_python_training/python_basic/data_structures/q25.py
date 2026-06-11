"""
Program to find sum, maximum value, sorted list,
and remove duplicates from a list.
"""


def perform_list_operations():
    """
    Perform different operations on a list.
    """
    numbers = [10, 25, 10, 40, 15, 25, 60, 80, 40, 90]

    print("Original List:", numbers)
    print("Sum:", sum(numbers))
    print("Maximum Number:", max(numbers))
    print("Sorted List:", sorted(numbers))

    unique_numbers = list(set(numbers))
    print("List After Removing Duplicates:", unique_numbers)


if __name__ == "__main__":
    perform_list_operations()