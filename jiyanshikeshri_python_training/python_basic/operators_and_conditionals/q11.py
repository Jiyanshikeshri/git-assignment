"""
Check whether a year is a leap year.
"""


def check_leap_year():
    """
    Check whether a year is a leap year.
    """
    year = int(input("Enter a year: "))

    # A leap year is divisible by 400,
    # or divisible by 4 but not divisible by 100.
    if (year % 400 == 0) or (year % 4 == 0 and year % 100 != 0):
        print("Leap Year")
    else:
        print("Not a Leap Year")


if __name__ == "__main__":
    check_leap_year()