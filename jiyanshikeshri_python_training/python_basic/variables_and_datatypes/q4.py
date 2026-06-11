"""
Create variables of type int, float, string, and boolean.
Print their types using type().
"""


def display_variable_types():
    integer_value = 22
    float_value = 22.5
    string_value = "Python"
    boolean_value = True

    print(type(integer_value))
    print(type(float_value))
    print(type(string_value))
    print(type(boolean_value))


if __name__ == "__main__":
    display_variable_types()