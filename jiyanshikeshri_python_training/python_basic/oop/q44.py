"""
Program to demonstrate polymorphism using different classes.
"""

class Dog:
    """
    Represents a dog.
    """

    def make_sound(self):
        """
        Display dog sound.
        """
        print("Dog sounds: Bark")


class Cat:
    """
    Represents a cat.
    """

    def make_sound(self):
        """
        Display cat sound.
        """
        print("Cat sounds: Meow")


def demonstrate_polymorphism():
    """
    Calling the same method on different objects.
    """
    animals = [Dog(), Cat()]

    for animal in animals:
        animal.make_sound()


if __name__ == "__main__":
    demonstrate_polymorphism()