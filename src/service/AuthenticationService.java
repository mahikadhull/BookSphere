
package service;

import dao.StudentDAO;
import model.Librarian;
import model.Student;
import model.User;

public class AuthenticationService {

    private StudentDAO studentDAO;
    private Librarian librarian;

    // Constructor
    public AuthenticationService(StudentDAO studentDAO,
                                 Librarian librarian) {

        this.studentDAO = studentDAO;
        this.librarian = librarian;

    }

    // Authenticate student or librarian
    public User login(String email, String password) {

        if (email == null || password == null) {
            return null;
        }

        // First check whether the user is a student
        Student student = studentDAO.findStudentByEmail(email);

        if (student != null) {

            if (student.getPassword().equals(password)) {

                return student;

            }

        }

        // If not a student, check librarian
        if (librarian != null) {

            if (librarian.getEmail().equalsIgnoreCase(email) &&
                librarian.getPassword().equals(password)) {

                return librarian;

            }

        }

        // Invalid email or password
        return null;

    }

}
