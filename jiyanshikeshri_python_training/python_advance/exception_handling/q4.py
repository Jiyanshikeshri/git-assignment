"""
Program to handle multiple exceptions in a single program.
"""

def handle_multiple_exceptions():
    """
    Handling of different exception types.
    """
    try:
        number = int(input("Enter a number: "))
        result = 100 / number

        file = open("example.txt", "r")
        print(file.read())

        file.close()
        print(result)

    except ValueError:
        print("Invalid integer entered.")

    except ZeroDivisionError:
        print("Division by zero is not allowed.")

    except FileNotFoundError:
        print("Requested file does not exist.")

if __name__ == "__main__":
    handle_multiple_exceptions()