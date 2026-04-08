import java.util.Scanner;

public class Factorial {

    // Method to calculate factorial
    static long calculateFactorial(int num) {
        long fact = 1;

        for (int i = 1; i <= num; i++) {
            fact = fact * i;
        }

        return fact;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = sc.nextInt();

        if (number < 0) {
            System.out.println("Factorial not defined for negative numbers");
        } else {
            long result = calculateFactorial(number);
            System.out.println("Factorial of " + number + " is = " + result);
        }

        sc.close();
    }
}