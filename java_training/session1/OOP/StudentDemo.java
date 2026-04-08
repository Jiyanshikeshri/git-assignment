import java.util.Scanner;

// Parent class
class Student {
    String name;
    int rollNumber;
    double marks;

    // Constructor
    Student(String name, int rollNumber, double marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
    }

    // Method to display student details
    void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Marks: " + marks);
    }
}

// Child class
class GraduateStudent extends Student {
    String specialization;

    // Constructor
    GraduateStudent(String name, int rollNumber, double marks, String specialization) {
        super(name, rollNumber, marks);
        this.specialization = specialization;
    }

    // Method to display graduate details
    void displayGraduateDetails() {
        displayDetails();
        System.out.println("Specialization: " + specialization);
    }
}

// Main class
public class StudentDemo{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input basic student details
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter roll number: ");
        int roll = sc.nextInt();

        System.out.print("Enter marks: ");
        double marks = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter specialization: ");
        String spec = sc.nextLine();

        // Create object of GraduateStudent
        GraduateStudent gs = new GraduateStudent(name, roll, marks, spec);

        System.out.println("\nStudent Details:");
        gs.displayGraduateDetails();

        sc.close();
    }
}