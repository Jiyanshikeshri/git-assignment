public class Datatypes{
    public static void main(String[] args) {

        // Primitive Data Types
        int num = 10;
        double price = 102.34;
        char grade = 'A';
        boolean isJavaEasy = true;

        System.out.println("Primitive Data Types:");
        System.out.println(num);
        System.out.println(price);
        System.out.println(grade);
        System.out.println(isJavaEasy);

        // Reference Data Types
        String name = "Jiyanshi";
        int[] numbers = {1, 2, 3};

        System.out.println("\nReference Data Types:");
        System.out.println(name);

        for (int n : numbers) {
            System.out.print(n + " ");
        }
    }
}