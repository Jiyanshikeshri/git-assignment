"""
Pytest test cases for a function that adds two numbers.
"""

def add_numbers(a, b):
    """
    Return the sum of two numbers
    """
    return a + b


def test_add_positive_numbers():
    """
    Test addition of two positive numbers
    """
    assert add_numbers(1, 2) == 3


def test_add_negative_numbers():
    """
    Test addition of two negative numbers
    """
    assert add_numbers(-5, -3) == -8


def test_add_zero():
    """
    Test addition when one operand is zero
    """
    assert add_numbers(7, 0) == 7