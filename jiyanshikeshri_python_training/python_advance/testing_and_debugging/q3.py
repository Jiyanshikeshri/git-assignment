"""
Program demonstrating the use of pdb to identify a logical bug.
"""

import pdb

def calculate_average(numbers):
    """
    Return the average of numbers

    The divisor is intentionally incorrect for debugging
    """
    pdb.set_trace()

    # Logical bug is it should divide by len(numbers)
    average = sum(numbers) / (len(numbers) - 1)

    return average


if __name__ == "__main__":
    values = [10, 20, 30, 40]
    print(calculate_average(values))