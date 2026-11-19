
package ui;

import java.util.Scanner;

public class InputHandler {

    private Scanner scanner;

    public InputHandler() {
        scanner = new Scanner(System.in);
    }

    // Read integer input
    public int getIntInput(String message) {

        while (true) {
            System.out.print(message);

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } else {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    // Read string input
    public String getStringInput(String message) {

        System.out.print(message);
        return scanner.nextLine();
    }

    // Read non-empty string input
    public String getNonEmptyInput(String message) {

        while (true) {
            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty. Try again.");
        }
    }

    // Read yes/no input
    public boolean getYesNoInput(String message) {

        while (true) {
            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("yes") ||
                input.equalsIgnoreCase("y")) {
                return true;
            }

            if (input.equalsIgnoreCase("no") ||
                input.equalsIgnoreCase("n")) {
                return false;
            }

            System.out.println("Please enter yes or no.");
        }
    }
}
