"""
Program to calculate the factorial of a number using recursion.
"""

def factorial(number):
    """
    Returns the factorial of the given number using recursion.
    """
    # Base case is factorial of 0 and 1 is 1.
    if number == 0 or number == 1:
        return 1

    # Recursive case is n! = n * (n-1)!
    return number * factorial(number - 1)


if __name__ == "__main__":
    user_number = int(input("Enter a number: "))
    print(f"Factorial: {factorial(user_number)}")