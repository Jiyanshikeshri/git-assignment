"""
Program to demonstrate the difference between an iterator and a generator.
"""

def compare_iterator_and_generator():
    """
    Display examples of an iterator and a generator and explain the differences.
    """
    numbers = [1, 2, 3]
    iterator = iter(numbers)

    print("Iterator Output:")
    print(next(iterator))
    print(next(iterator))

    print("\nGenerator Output:")

    generator = (number for number in numbers)

    print(next(generator))
    print(next(generator))

    # Iterators are created using iter(), while generators use yield or generator expressions and generate values lazily.


if __name__ == "__main__":
    compare_iterator_and_generator()