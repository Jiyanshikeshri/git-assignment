"""
Program to generate Fibonacci numbers using a generator.
"""

def fibonacci_generator(limit):
    """
    Yield Fibonacci numbers up to the given limit.
    """
    first = 0
    second = 1

    for _ in range(limit):
        yield first
        first, second = second, first + second


def display_fibonacci():
    """
    For printing Fibonacci numbers
    """
    for number in fibonacci_generator(10):
        print(number)


if __name__ == "__main__":
    display_fibonacci()