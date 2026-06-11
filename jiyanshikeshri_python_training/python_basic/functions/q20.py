"""
Program demonstrating default parameters in a function.

SRP Followed:
greet_user() only creates greeting message.
display_message() only displays output.
"""


def greet_user(name="Guest"):
    """
    Return greeting message.
    """
    return f"Welcome, {name}!"


def display_message(message):
    """
    Display greeting message.
    """
    print(message)


if __name__ == "__main__":
    display_message(greet_user())
    display_message(greet_user("Jiyanshi"))