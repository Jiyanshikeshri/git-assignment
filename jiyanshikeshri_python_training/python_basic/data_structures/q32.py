"""
Program to create a student dictionary and access values.
"""

def access_dictionary_values():
    """
    Create a dictionary and display its values.
    """
    student = {
        "name": "Jiyanshi",
        "age": 22,
        "course": "python training"
    }

    print("Student Name:", student["name"])
    print("Student Age:", student["age"])
    print("Student Course:", student["course"])


if __name__ == "__main__":
    access_dictionary_values()