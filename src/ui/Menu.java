
package ui;

import java.util.Scanner;

public class Menu {

    public void displayMenu() {

        System.out.println("\n================================");
        System.out.println("       LIBRARY MANAGEMENT");
        System.out.println("================================");

        System.out.println("1. Admin Login");
        System.out.println("2. Student Login");
        System.out.println("3. Register as New Student");
        System.out.println("4. Exit");

        System.out.println("================================");
        System.out.print("Enter your choice: ");
    }

    public int getChoice(Scanner scanner) {

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.next();
            System.out.print("Enter your choice: ");
        }

        return scanner.nextInt();
    }
}
