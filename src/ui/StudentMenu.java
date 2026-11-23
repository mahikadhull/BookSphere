
package ui;

import model.Student;

public class StudentMenu {

    private InputHandler inputHandler;

    public StudentMenu() {
        inputHandler = new InputHandler();
    }

    public void displayMenu(Student student) {

        if (student == null) {
            System.out.println("No student is logged in.");
            return;
        }

        boolean running = true;

        while (running) {

            System.out.println("\n================================");
            System.out.println("          STUDENT MENU");
            System.out.println("================================");

            System.out.println("Welcome, " + student.getName());

            System.out.println("\n1. View My Profile");
            System.out.println("2. Logout");

            System.out.println("================================");

            int choice = inputHandler.getIntInput("Enter your choice: ");

            switch (choice) {

                case 1:
                    displayProfile(student);
                    break;

                case 2:
                    System.out.println("\nYou have been logged out successfully.");
                    running = false;
                    break;

                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    private void displayProfile(Student student) {

        System.out.println("\n================================");
        System.out.println("         STUDENT PROFILE");
        System.out.println("================================");

        System.out.println("User ID: " + student.getUserId());
        System.out.println("Name: " + student.getName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Roll Number: " + student.getRollNumber());
        System.out.println("Department: " + student.getDepartment());
        System.out.println("Academic Year: " + student.getAcademicYear());
        System.out.println("Borrowed Books: " + student.getBorrowedBookCount());

        System.out.println("================================");
    }
}
