"""
Program demonstrating pdb breakpoints inside a loop.
"""

import pdb

def print_numbers():
    """
    Print numbers from 1 to 5 while inspecting variables.
    """
    for number in range(1, 6):
        # Pause execution during each iteration for inspection
        pdb.set_trace()
        print(number)


if __name__ == "__main__":
    print_numbers()