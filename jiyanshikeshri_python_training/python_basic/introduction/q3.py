"""
Program to take user name and age as input and display a formatted message.
"""


def display_user_details():
    """
    Take user input and display a formatted message.
    """
    name = input("Enter your name: ")
    age = int(input("Enter your age: "))

    print(f"Hello {name}. You are {age} years old.")


if __name__ == "__main__":
    display_user_details()