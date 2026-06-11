"""
Find factorial of a number.
"""


def calculate_factorial():
    """
    Calculate and display the factorial of a number
    """
    number = int(input("Enter a number: "))
    factorial = 1

    # Multiply all numbers from 1 to the given number.
    for value in range(1, number + 1):
        factorial = factorial * value

    print("Factorial =", factorial)


if __name__ == "__main__":
    calculate_factorial()