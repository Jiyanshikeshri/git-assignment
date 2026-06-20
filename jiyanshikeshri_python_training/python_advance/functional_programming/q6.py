"""
Program to calculate Fibonacci numbers using recursion.
"""

def fibonacci(number):
    """
    Returns the Fibonacci number
    """
    # Base cases for the first two Fibonacci numbers
    if number == 0:
        return 0

    if number == 1:
        return 1

    # Recursive relation is nth Fibonacci number is the sum of the two preceding ones
    return fibonacci(number - 1) + fibonacci(number - 2)


if __name__ == "__main__":
    user_number = int(input("Enter the position: "))
    print(f"Fibonacci number: {fibonacci(user_number)}")