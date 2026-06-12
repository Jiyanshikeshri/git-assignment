"""
Program to create a Student class and display student details.
"""

class Student:
    """
    Represents a student with basic details.
    """

    def __init__(self, name, age, course):
        self.name = name
        self.age = age
        self.course = course

    def display_details(self):
        """
        Display student details.
        """
        print("Name:", self.name)
        print("Age:", self.age)
        print("Course:", self.course)


def demonstrate_student_class():
    """
    Creating a Student object and displaying its details.
    """
    student = Student("Jiyanshi", 22, "Python Training")
    student.display_details()


if __name__ == "__main__":
    demonstrate_student_class()