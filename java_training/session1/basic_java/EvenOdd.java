import java.util.Scanner;

public class EvenOdd {

    // Method to check even or odd
    static void checkEvenOdd(int num) {
        if (num % 2 == 0) {
            System.out.println(num + " is an Even number.");
        } else {
            System.out.println(num + " is an Odd number.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = sc.nextInt();

        checkEvenOdd(number);

        sc.close();
    }
}