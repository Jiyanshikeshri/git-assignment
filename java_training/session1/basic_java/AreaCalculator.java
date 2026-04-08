import java.util.Scanner;

// Base class
class Shape {
    void calculateArea() {
        System.out.println("Calculating area :-");
    }
}

// Circle class
class Circle extends Shape {
    double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    void calculateArea() {
        double area = Math.PI * radius * radius;
        System.out.println("Area of Circle:- " + area);
    }
}

// Rectangle class
class Rectangle extends Shape {
    double length, width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    void calculateArea() {
        double area = length * width;
        System.out.println("Area of Rectangle:- " + area);
    }
}

// Triangle class
class Triangle extends Shape {
    double base, height;

    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    void calculateArea() {
        double area = 0.5 * base * height;
        System.out.println("Area of Triangle: " + area);
    }
}

// Main class
public class AreaCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose shape:-");
        System.out.println("1. Circle");
        System.out.println("2. Rectangle");
        System.out.println("3. Triangle");

        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter radius: ");
                double r = sc.nextDouble();
                Circle c = new Circle(r);
                c.calculateArea();
                break;

            case 2:
                System.out.print("Enter length: ");
                double l = sc.nextDouble();
                System.out.print("Enter width: ");
                double w = sc.nextDouble();
                Rectangle rect = new Rectangle(l, w);
                rect.calculateArea();
                break;

            case 3:
                System.out.print("Enter base: ");
                double b = sc.nextDouble();
                System.out.print("Enter height: ");
                double h = sc.nextDouble();
                Triangle t = new Triangle(b, h);
                t.calculateArea();
                break;

            default:
                System.out.println("Invalid choice");
        }
        sc.close();
    }
}