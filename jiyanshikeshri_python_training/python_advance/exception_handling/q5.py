"""
Program to catch all exceptions and print the error message.
"""

def catch_all_exceptions():
    """
    Generic exception handling.
    """
    try:
        number = int(input("Enter a number: "))
        result = 10 / number
        print(result)

    except Exception as error:
        # Prints the actual exception message.
        print(f"An error occurred: {error}")

if __name__ == "__main__":
    catch_all_exceptions()