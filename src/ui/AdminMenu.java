
package ui;

import java.util.Scanner;

public class AdminMenu {

    public void displayAdminMenu() {

        System.out.println("\n================================");
        System.out.println("          ADMIN MENU");
        System.out.println("================================");

        System.out.println("1. Manage Books");
        System.out.println("2. Manage Students");
        System.out.println("3. View All Books");
        System.out.println("4. View All Students");
        System.out.println("5. View Reports");
        System.out.println("6. Logout");

        System.out.println("================================");
        System.out.print("Enter your choice: ");
    }

    public int getAdminChoice(Scanner scanner) {

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Enter a number.");
            scanner.next();
            System.out.print("Enter your choice: ");
        }

        return scanner.nextInt();
    }
}
