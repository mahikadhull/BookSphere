package main;

import java.util.Scanner;

import dao.StudentDAO;
import model.Librarian;
import model.Student;
import model.User;
import service.AuthenticationService;
import ui.AdminMenu;
import ui.Menu;
import ui.StudentMenu;

/**
 * Main entry point for the BookSphere Library Management System.
 */
public class MainApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        StudentDAO studentDAO = new StudentDAO();

        Librarian librarian = new Librarian(
                "A001", "Library Admin", "admin@booksphere.com",
                "admin123", "EMP001", "Administrator");

        // Demo account for testing. Remove this block when real registration is used.
        studentDAO.addStudent(new Student(
                "S001", "Demo Student", "student@booksphere.com",
                "student123", "STU001", "CSE", 2));

        AuthenticationService authenticationService =
                new AuthenticationService(studentDAO, librarian);

        Menu menu = new Menu();
        AdminMenu adminMenu = new AdminMenu();
        StudentMenu studentMenu = new StudentMenu();

        boolean running = true;

        System.out.println("================================");
        System.out.println("       WELCOME TO BOOKSPHERE");
        System.out.println("================================");

        while (running) {
            menu.displayMenu();
            int choice = menu.getChoice(scanner);
            scanner.nextLine(); // Consume newline after nextInt().

            switch (choice) {
                case 1:
                    login(scanner, authenticationService, adminMenu, studentMenu, true);
                    break;
                case 2:
                    login(scanner, authenticationService, adminMenu, studentMenu, false);
                    break;
                case 3:
                    registerStudent(scanner, studentDAO);
                    break;
                case 4:
                    running = false;
                    System.out.println("Thank you for using BookSphere!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, 3 or 4.");
            }
        }

        scanner.close();
    }

    private static void registerStudent(Scanner scanner, StudentDAO studentDAO) {
        System.out.println("\\n========== STUDENT REGISTRATION ==========");

        System.out.print("Enter student ID: ");
        String userId = scanner.nextLine().trim();

        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter roll number: ");
        String rollNumber = scanner.nextLine().trim();

        System.out.print("Enter department: ");
        String department = scanner.nextLine().trim();

        System.out.print("Enter academic year (number): ");
        String yearInput = scanner.nextLine().trim();

        if (userId.isEmpty() || name.isEmpty() || email.isEmpty()
                || password.isEmpty() || rollNumber.isEmpty()
                || department.isEmpty()) {
            System.out.println("All fields are required. Registration cancelled.");
            return;
        }

        int academicYear;
        try {
            academicYear = Integer.parseInt(yearInput);
        } catch (NumberFormatException e) {
            System.out.println("Academic year must be a number. Registration cancelled.");
            return;
        }

        if (academicYear < 1 || academicYear > 8) {
            System.out.println("Please enter a valid academic year (1-8).");
            return;
        }

        if (studentDAO.emailExists(email)) {
            System.out.println("This email is already registered.");
            return;
        }

        if (studentDAO.rollNumberExists(rollNumber)) {
            System.out.println("This roll number is already registered.");
            return;
        }

        Student student = new Student(
                userId, name, email, password,
                rollNumber, department, academicYear);

        studentDAO.addStudent(student);

        System.out.println("\\nRegistration successful!");
        System.out.println("You can now log in using your registered email and password.");
    }

    private static void login(Scanner scanner,
                              AuthenticationService authenticationService,
                              AdminMenu adminMenu,
                              StudentMenu studentMenu,
                              boolean adminLogin) {

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = authenticationService.login(email, password);

        if (user == null) {
            System.out.println("Invalid email or password.");
            return;
        }

        if (adminLogin && user instanceof Librarian) {
            System.out.println("\\nWelcome, " + user.getName() + "!");
            boolean loggedIn = true;

            while (loggedIn) {
                adminMenu.displayAdminMenu();
                int adminChoice = adminMenu.getAdminChoice(scanner);
                scanner.nextLine(); // Consume newline.

                switch (adminChoice) {
                    case 1:
                        System.out.println("Manage Books module is not connected yet.");
                        break;
                    case 2:
                        System.out.println("Manage Students module is not connected yet.");
                        break;
                    case 3:
                        System.out.println("View All Books module is not connected yet.");
                        break;
                    case 4:
                        System.out.println("View All Students module is not connected yet.");
                        break;
                    case 5:
                        System.out.println("Reports module is not connected yet.");
                        break;
                    case 6:
                        loggedIn = false;
                        System.out.println("Admin logged out successfully.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else if (!adminLogin && user instanceof Student) {
            System.out.println("\\nWelcome, " + user.getName() + "!");
            studentMenu.displayMenu((Student) user);
        } else {
            System.out.println(adminLogin
                    ? "This account is not an admin account."
                    : "This account is not a student account.");
        }
    }
}
