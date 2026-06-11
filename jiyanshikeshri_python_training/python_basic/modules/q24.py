"""
Program to import and use a custom module.
"""

from custom_module import greet_user


def display_greeting():
    """
    Display greeting from custom module.
    """
    name = input("Enter your name: ")
    print(greet_user(name))


if __name__ == "__main__":
    display_greeting()