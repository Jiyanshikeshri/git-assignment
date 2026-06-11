"""
Program to merge two dictionaries.
"""


def merge_dictionaries():
    """
    Merge two dictionaries into one.
    """
    student = {
        "name": "Jiyanshi",
        "age": 22
    }

    course = {
        "course": "python training",
        "Organisation": "NucleusTeq"
    }

    merged_dictionary = student | course

    print("Merged Dictionary:", merged_dictionary)


if __name__ == "__main__":
    merge_dictionaries()