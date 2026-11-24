package main;

import dao.BookDAO;
import dao.StudentDAO;
import exception.DataAccessException;
import java.util.List;
import java.util.Scanner;
import model.Book;
import model.BookCopy;
import model.Category;
import model.Librarian;
import model.ShelfLocation;
import model.Student;
import model.User;
import service.AuthenticationService;
import ui.AdminMenu;
import ui.Menu;
import ui.StudentMenu;

/** Main entry point for the BookSphere Library Management System. */
public class MainApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        StudentDAO studentDAO = new StudentDAO();
        BookDAO bookDAO = new BookDAO();

        Librarian librarian = new Librarian(
                "A001", "Library Admin", "admin@booksphere.com",
                "admin123", "EMP001", "Administrator");

        // Demo account for testing.
        if (!studentDAO.emailExists("student@booksphere.com")) {
            studentDAO.addStudent(new Student(
                    "S001", "Demo Student", "student@booksphere.com",
                    "student123", "STU001", "CSE", 2));
        }

        AuthenticationService authenticationService =
                new AuthenticationService(studentDAO, librarian);

        Menu menu = new Menu();
        AdminMenu adminMenu = new AdminMenu();
        StudentMenu studentMenu = new StudentMenu(scanner);

        System.out.println("================================");
        System.out.println("       WELCOME TO BOOKSPHERE");
        System.out.println("================================");

        boolean running = true;
        while (running) {
            menu.displayMenu();
            int choice = readInt(scanner);

            switch (choice) {
                case 1:
                    login(scanner, authenticationService, adminMenu, studentMenu,
                            studentDAO, bookDAO, true);
                    break;
                case 2:
                    login(scanner, authenticationService, adminMenu, studentMenu,
                            studentDAO, bookDAO, false);
                    break;
                case 3:
                    registerStudent(scanner, studentDAO);
                    break;
                case 4:
                    running = false;
                    System.out.println("Thank you for using BookSphere.");
                    System.out.println("We wish to see you back soon! :)");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, 3 or 4.");
            }
        }
        scanner.close();
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.nextLine();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private static void registerStudent(Scanner scanner, StudentDAO studentDAO) {
        System.out.println("\n========== STUDENT REGISTRATION ==========");
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
        System.out.print("Enter academic year (1-4): ");
        int academicYear = readInt(scanner);

        if (userId.isEmpty() || name.isEmpty() || email.isEmpty()
                || password.isEmpty() || rollNumber.isEmpty() || department.isEmpty()) {
            System.out.println("All fields are required. Registration cancelled.");
            return;
        }
        if (academicYear < 1 || academicYear > 4) {
            System.out.println("Please enter a valid academic year (1-4).");
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

        studentDAO.addStudent(new Student(userId, name, email, password,
                rollNumber, department, academicYear));
        System.out.println("\nRegistration successful! You can now log in.");
    }

    private static void login(Scanner scanner,
                              AuthenticationService authenticationService,
                              AdminMenu adminMenu,
                              StudentMenu studentMenu,
                              StudentDAO studentDAO,
                              BookDAO bookDAO,
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
            System.out.println("\nWelcome, " + user.getName() + "!");
            boolean loggedIn = true;
            while (loggedIn) {
                adminMenu.displayAdminMenu();
                int choice = readInt(scanner);
                try {
                    switch (choice) {
                        case 1: manageBooks(scanner, bookDAO); break;
                        case 2: manageStudents(scanner, studentDAO); break;
                        case 3: displayBooks(bookDAO); break;
                        case 4: displayStudents(studentDAO); break;
                        case 5: displayReports(bookDAO, studentDAO); break;
                        case 6:
                            loggedIn = false;
                            System.out.println("Admin logged out successfully.");
                            break;
                        default: System.out.println("Invalid choice. Please try again.");
                    }
                } catch (DataAccessException e) {
                    System.out.println("Book storage error: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid data: " + e.getMessage());
                }
            }
        } else if (!adminLogin && user instanceof Student) {
            System.out.println("\nWelcome, " + user.getName() + "!");
            studentMenu.displayMenu((Student) user);
        } else {
            System.out.println(adminLogin
                    ? "This account is not an admin account."
                    : "This account is not a student account.");
        }
    }

    private static void manageBooks(Scanner scanner, BookDAO bookDAO)
            throws DataAccessException {
        boolean back = false;
        while (!back) {
            System.out.println("\n========== MANAGE BOOKS ==========");
            System.out.println("1. Add Book\n2. Update Book\n3. Delete Book\n4. Search Book\n5. Back");
            System.out.print("Enter your choice: ");
            int choice = readInt(scanner);
            switch (choice) {
                case 1: {
                    System.out.print("Book ID: "); String id = scanner.nextLine().trim();
                    System.out.print("Title: "); String title = scanner.nextLine().trim();
                    System.out.print("Author: "); String author = scanner.nextLine().trim();
                    System.out.print("ISBN: "); String isbn = scanner.nextLine().trim();
                    System.out.print("Publisher: "); String publisher = scanner.nextLine().trim();
                    System.out.print("Publication year: "); int year = readInt(scanner);
                    System.out.print("Category: "); String categoryName = scanner.nextLine().trim();
                    System.out.print("Floor: "); String floor = scanner.nextLine().trim();
                    System.out.print("Rack number: "); String rack = scanner.nextLine().trim();
                    System.out.print("Shelf number: "); String shelf = scanner.nextLine().trim();
                    System.out.print("Number of copies: "); int copies = readInt(scanner);
                    if (id.isEmpty() || title.isEmpty() || author.isEmpty() || isbn.isEmpty()
                            || publisher.isEmpty() || categoryName.isEmpty() || copies <= 0) {
                        System.out.println("Please enter valid, non-empty book details.");
                        break;
                    }
                    Category category = new Category("CAT-" + id, categoryName, "");
                    ShelfLocation location = new ShelfLocation("LOC-" + id, rack, shelf, floor);
                    Book book = new Book(id, title, author, isbn, publisher, year, category, location);
                    for (int i = 1; i <= copies; i++) book.addCopy(new BookCopy(id + "-C" + i));
                    if (bookDAO.addBook(book)) System.out.println("Book added successfully.");
                    else System.out.println("A book with this ID already exists.");
                    break;
                }
                case 2: {
                    System.out.print("Enter book ID to update: ");
                    String id = scanner.nextLine().trim();
                    Book old = bookDAO.findBookById(id);
                    if (old == null) { System.out.println("Book not found."); break; }
                    System.out.print("New title: "); old.setTitle(scanner.nextLine().trim());
                    System.out.print("New author: "); old.setAuthor(scanner.nextLine().trim());
                    System.out.print("New ISBN: "); old.setIsbn(scanner.nextLine().trim());
                    System.out.print("New publisher: "); old.setPublisher(scanner.nextLine().trim());
                    System.out.print("New publication year: "); old.setPublicationYear(readInt(scanner));
                    System.out.println(bookDAO.updateBook(old) ? "Book updated successfully." : "Update failed.");
                    break;
                }
                case 3:
                    System.out.print("Enter book ID to delete: ");
                    System.out.println(bookDAO.deleteBook(scanner.nextLine().trim())
                            ? "Book deleted successfully." : "Book not found.");
                    break;
                case 4:
                    System.out.print("Enter title, author or ISBN: ");
                    List<Book> matches = bookDAO.searchBooks(scanner.nextLine().trim());
                    if (matches.isEmpty()) System.out.println("No matching books found.");
                    else for (Book book : matches) System.out.println("\n" + book + "\n----------------");
                    break;
                case 5: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void manageStudents(Scanner scanner, StudentDAO studentDAO) {
        boolean back = false;
        while (!back) {
            System.out.println("\n========== MANAGE STUDENTS ==========");
            System.out.println("1. Add Student\n2. Update Student\n3. Delete Student\n4. Search Student\n5. Back");
            System.out.print("Enter your choice: ");
            int choice = readInt(scanner);
            switch (choice) {
                case 1: registerStudent(scanner, studentDAO); break;
                case 2: {
                    System.out.print("Enter roll number of student to update: ");
                    String roll = scanner.nextLine().trim();
                    Student student = studentDAO.findStudentByRollNumber(roll);
                    if (student == null) { System.out.println("Student not found."); break; }
                    System.out.print("New name: "); student.setName(scanner.nextLine().trim());
                    System.out.print("New email: "); student.setEmail(scanner.nextLine().trim());
                    System.out.print("New department: "); student.setDepartment(scanner.nextLine().trim());
                    System.out.print("New academic year (1-4): ");
                    int year = readInt(scanner);
                    if (year < 1 || year > 4) { System.out.println("Invalid academic year."); break; }
                    student.setAcademicYear(year);
                    studentDAO.updateStudent(student);
                    System.out.println("Student updated successfully.");
                    break;
                }
                case 3:
                    System.out.print("Enter roll number to delete: ");
                    System.out.println(studentDAO.deleteStudentByRollNumber(scanner.nextLine().trim())
                            ? "Student deleted successfully." : "Student not found.");
                    break;
                case 4:
                    System.out.print("Enter roll number: ");
                    Student found = studentDAO.findStudentByRollNumber(scanner.nextLine().trim());
                    if (found == null) System.out.println("Student not found.");
                    else printStudent(found);
                    break;
                case 5: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void displayBooks(BookDAO bookDAO) throws DataAccessException {
        List<Book> books = bookDAO.getAllBooks();
        System.out.println("\n========== ALL BOOKS ==========");
        if (books.isEmpty()) System.out.println("No books have been added yet.");
        else for (Book book : books) System.out.println("\n" + book + "\n----------------------------");
    }

    private static void displayStudents(StudentDAO studentDAO) {
        System.out.println("\n========== ALL STUDENTS ==========");
        List<Student> students = studentDAO.getAllStudents();
        if (students.isEmpty()) System.out.println("No students are registered.");
        else for (Student student : students) printStudent(student);
    }

    private static void printStudent(Student student) {
        System.out.println("Student ID: " + student.getUserId()
                + "\nName: " + student.getName()
                + "\nEmail: " + student.getEmail()
                + "\nRoll Number: " + student.getRollNumber()
                + "\nDepartment: " + student.getDepartment()
                + "\nAcademic Year: " + student.getAcademicYear()
                + "\nBorrowed Books: " + student.getBorrowedBookCount()
                + "\n----------------------------");
    }

    private static void displayReports(BookDAO bookDAO, StudentDAO studentDAO)
            throws DataAccessException {
        List<Book> books = bookDAO.getAllBooks();
        List<Student> students = studentDAO.getAllStudents();
        int copies = 0;
        int available = 0;
        for (Book book : books) {
            copies += book.getTotalCopies();
            available += book.getAvailableCopies();
        }
        System.out.println("\n========== LIBRARY REPORT ==========");
        System.out.println("Different book titles: " + books.size());
        System.out.println("Total physical copies: " + copies);
        System.out.println("Available copies: " + available);
        System.out.println("Registered students: " + students.size());
        System.out.println("====================================");
    }
}
