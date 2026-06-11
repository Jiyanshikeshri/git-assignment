"""
Program to generate random numbers using random module.
"""

import random


def generate_random_numbers():
    """
    Generate and display random numbers.
    """
    print("Random Number:", random.randint(1, 100))
    print("Random Float:", random.random())


if __name__ == "__main__":
    generate_random_numbers()