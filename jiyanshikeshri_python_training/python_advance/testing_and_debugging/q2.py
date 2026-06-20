"""
Pytest test cases for checking whether a number is prime.
"""

def is_prime(number):
    """
    Return True if the number is prime, otherwise False
    """
    if number < 2:
        return False

    for divisor in range(2, int(number ** 0.5) + 1):
        if number % divisor == 0:
            return False

    return True


def test_prime_number():
    """
    Test a prime number
    """
    assert is_prime(13) is True


def test_non_prime_number():
    """
    Test a non-prime number
    """
    assert is_prime(12) is False


def test_number_less_than_two():
    """
    Test numbers that are not prime
    """
    assert is_prime(1) is False