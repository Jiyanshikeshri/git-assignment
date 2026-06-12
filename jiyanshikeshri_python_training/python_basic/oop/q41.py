"""
Program to create a Car class with a constructor.
"""

class Car:
    """
    Represents a car.
    """

    def __init__(self, brand, model):
        self.brand = brand
        self.model = model

    def display_details(self):
        """
        Display car details.
        """
        print("Brand:", self.brand)
        print("Model:", self.model)


def demonstrate_car_class():
    """
    Creating a Car object and displaying its details.
    """
    car = Car("Toyota", "Fortuner")
    car.display_details()


if __name__ == "__main__":
    demonstrate_car_class()