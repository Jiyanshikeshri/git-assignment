"""
Program to demonstrate inheritance using Person and Employee classes.
"""

class Person:
    """
    Represents a person.
    """

    def __init__(self, name, age):
        self.name = name
        self.age = age


class Employee(Person):
    """
    Represents an employee inherited from Person.
    """

    def __init__(self, name, age, employee_id):
        super().__init__(name, age)
        self.employee_id = employee_id

    def display_details(self):
        """
        Displaying employee details.
        """
        print("Name:", self.name)
        print("Age:", self.age)
        print("Employee ID:", self.employee_id)


def demonstrate_inheritance():
    """
    Creating an Employee object and displaying its details.
    """
    employee = Employee("Jiyanshi", 22, 12)
    employee.display_details()


if __name__ == "__main__":
    demonstrate_inheritance()