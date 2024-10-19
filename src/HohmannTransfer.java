import java.util.InputMismatchException;
import java.util.Scanner;

public class HohmannTransfer {
    static double GM = 3.5316 * Math.pow(10, 12);
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean needAp = true, needPe = true, needParams = true;


        double ap = 0, pe = 0;

        while (needParams) {
            System.out.println("Input the current orbit's values in meters");
            while (needAp) {
                System.out.print(" Apoapsis: ");
                try {
                    double input = scanner.nextDouble();
                    if (input >= 70000) {
                        ap = input;
                        needAp = false;
                    } else {
                        System.out.println("Invalid Input: Apoapsis must be at least 70,000!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input: not a valid apoapsis!");
                    scanner.next(); // Consume the invalid token
                }
            }

            while (needPe) {
                System.out.print("Periapsis: ");
                try {
                    double input = scanner.nextDouble();
                    if (input >= 70000) {
                        pe = input;
                        needPe = false;
                    } else {
                        System.out.println("Invalid Input: Periapsis must be at least 70,000!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input: not a valid periapsis!");
                    scanner.next(); // Consume the invalid token
                }
            }

            if (pe >= ap) {
                System.out.println ("Periapsis is higher than apoapsis!");
                needAp = true;
                needPe = true;
                scanner.next();
            } else {
                needParams = false;
            }
        }
    }

    static void sleep (int milli){
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
 // keostationary == 2,863,334m