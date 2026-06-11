"""
Reverse a number using loop.
"""


def reverse_number():
    """
    Reverse the digits of the entered number.
    """
    number = int(input("Enter a number: "))
    rev_number = 0

    # Find the last digit of the number and add it to the reversed number, then remove the last digit from the original number. Repeat this process until the original number becomes 0.
    while number > 0:
        digit = number % 10
        rev_number = rev_number * 10 + digit
        number = number // 10

    print("Reversed Number =", rev_number)


if __name__ == "__main__":
    reverse_number()