"""
Program to demonstrate encapsulation using a Bank class.
"""

class Bank:
    """
    Represents a bank account with a private password.
    """

    def __init__(self, password, balance):
        self.__password = password
        self.__balance = balance

    def show_details(self):
        """
        Display the account private details.
        """
        print("Password:", self.__password)
        print("Balance:", self.__balance)


def demonstrate_encapsulation():
    """
    Creating a Bank object and accessing its private details through a method.
    """
    account = Bank("jiyanshi123", 5000)
    account.show_details()


if __name__ == "__main__":
    demonstrate_encapsulation()