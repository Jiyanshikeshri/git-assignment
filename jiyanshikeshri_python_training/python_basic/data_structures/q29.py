"""
Program to convert a tuple into a list and modify it.
"""

def modify_tuple():
    """
    Convert tuple to list and modify its contents.
    """
    fruits = ("Apple", "Banana", "Mango")

    fruits_list = list(fruits)
    fruits_list.append("Orange")

    print("Original Tuple:", fruits)
    print("Modified List:", fruits_list)


if __name__ == "__main__":
    modify_tuple()