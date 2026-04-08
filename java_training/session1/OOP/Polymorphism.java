// Parent class
class Parent {

    // Method 1
    void show(int a) {
        System.out.println("Parent method with one parameter: " + a);
    }
}

// Child class
class Child extends Parent {

    // Method 2 = same name, different parameters
    void show(int a, int b) {
        System.out.println("Child method with two parameters: " + (a + b));
    }
}

// Main class
public class Polymorphism{
    public static void main(String[] args) {

        Child obj = new Child();

        // Calling parent method
        obj.show(10);

        // Calling child method
        obj.show(10, 20);
    }
}