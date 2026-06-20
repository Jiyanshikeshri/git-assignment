"""
Program to process a large dataset using a generator.
"""

def large_dataset():
    """
    Yield numbers one at a time instead of storing all values in memory
    """
    for number in range(1, 10000):
        yield number


def process_dataset():
    """
    Process generated values without creating a large list in memory
    """
    count = 0

    for value in large_dataset():
        print(value)

        count += 1
        if count == 10:
            break

    # Using a generator saves memory because values are produced on demand only when needed


if __name__ == "__main__":
    process_dataset()