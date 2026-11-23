
package service;

import dao.StudentDAO;
import exception.StudentNotFoundException;
import java.util.ArrayList;
import model.Student;

public class StudentService {

    final private StudentDAO studentDAO;

    // Constructor
    public StudentService(StudentDAO studentDAO) {

        this.studentDAO = studentDAO;

    }

    // Register a new student
    public void registerStudent(Student student) {

        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (student.getUserId() == null ||
            student.getUserId().trim().isEmpty()) {

            throw new IllegalArgumentException("User ID is required");

        }

        if (student.getName() == null ||
            student.getName().trim().isEmpty()) {

            throw new IllegalArgumentException("Name is required");

        }

        if (student.getEmail() == null ||
            student.getEmail().trim().isEmpty()) {

            throw new IllegalArgumentException("Email is required");

        }

        if (student.getPassword() == null ||
            student.getPassword().trim().isEmpty()) {

            throw new IllegalArgumentException("Password is required");

        }

        if (student.getRollNumber() == null ||
            student.getRollNumber().trim().isEmpty()) {

            throw new IllegalArgumentException("Roll number is required");

        }

        if (student.getDepartment() == null ||
            student.getDepartment().trim().isEmpty()) {

            throw new IllegalArgumentException("Department is required");

        }

        if (student.getAcademicYear() < 1 ||
            student.getAcademicYear() > 4) {

            throw new IllegalArgumentException("Invalid academic year");

        }

        if (studentDAO.rollNumberExists(student.getRollNumber())) {

            throw new IllegalArgumentException(
                "Roll number already registered"
            );

        }

        if (studentDAO.emailExists(student.getEmail())) {

            throw new IllegalArgumentException(
                "Email already registered"
            );

        }

        studentDAO.addStudent(student);

    }

    // Search student using roll number
    public Student getStudentByRollNumber(String rollNumber)
            throws StudentNotFoundException {

        Student student = studentDAO.findStudentByRollNumber(rollNumber);

        if (student == null) {

            throw new StudentNotFoundException(
                "No student found with roll number: " + rollNumber
            );

        }

        return student;

    }

    // Search student using email
    public Student getStudentByEmail(String email)
            throws StudentNotFoundException {

        Student student = studentDAO.findStudentByEmail(email);

        if (student == null) {

            throw new StudentNotFoundException(
                "No student found with email: " + email
            );

        }

        return student;

    }

    // Get all registered students
    public ArrayList<Student> getAllStudents() {

        return studentDAO.getAllStudents();

    }

    // Update student information
    public void updateStudent(Student student)
            throws StudentNotFoundException {

        if (student == null) {

            throw new IllegalArgumentException("Student cannot be null");

        }

        boolean updated = studentDAO.updateStudent(student);

        if (!updated) {

            throw new StudentNotFoundException(
                "Student does not exist"
            );

        }

    }

}
