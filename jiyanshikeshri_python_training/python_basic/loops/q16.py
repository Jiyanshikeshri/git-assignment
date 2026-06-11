"""
Check whether a number is prime.
"""


def check_prime_number():
    """
    Check whether the entered number is prime.
    """
    number = int(input("Enter a number: "))

    if number <= 1:
        print("Not a Prime Number")
        return

    # A prime number is divisible only by 1 and a number itself
    for divisor in range(2, number):
        if number % divisor == 0:
            print("Not a Prime Number")
            return

    print("Prime Number")


if __name__ == "__main__":
    check_prime_number()