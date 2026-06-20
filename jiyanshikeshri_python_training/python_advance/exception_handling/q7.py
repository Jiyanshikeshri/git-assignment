"""
Program to create and use a custom AgeException.
"""

class AgeException(Exception):
    """
    Custom exception for invalid age.
    """
    pass


def validate_age():
    """
    Raise AgeException if age is less than 18.
    """
    age = int(input("Enter age: "))

    if age < 18:
        raise AgeException("Age must be 18 or above")

    print("Age is valid")


if __name__ == "__main__":
    try:
        validate_age()
    except AgeException as error:
        print(error)