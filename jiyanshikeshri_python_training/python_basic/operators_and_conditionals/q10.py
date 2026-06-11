"""
Calculate grade based on marks.
"""


def calculate_grade():
    """
    Calculate and display the grade based on marks.
    """
    marks = float(input("Enter marks: "))

    #if marks are 90 or above, the grade is A; if marks are 75 or above but less than 90, the grade is B; if marks are 50 or above but less than 75, the grade is C; otherwise, it is a fail.
    if marks >= 90:
        print("Grade A")
    elif marks >= 75:
        print("Grade B")
    elif marks >= 50:
        print("Grade C")
    else:
        print("Fail")


if __name__ == "__main__":
    calculate_grade()