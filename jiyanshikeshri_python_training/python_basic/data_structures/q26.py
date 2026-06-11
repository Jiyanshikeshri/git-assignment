"""
Program to count even and odd numbers in a list.
"""


def count_even_odd_numbers():
    """
    Count even and odd numbers from a list.
    """
    numbers = [12, 7, 5, 18, 20, 11, 9, 4, 16, 3, 2]

    even_count = 0
    odd_count = 0

    for number in numbers:
        if number % 2 == 0:
            even_count += 1
        else:
            odd_count += 1

    print("List:", numbers)
    print("Even Numbers Count:", even_count)
    print("Odd Numbers Count:", odd_count)


if __name__ == "__main__":
    count_even_odd_numbers()