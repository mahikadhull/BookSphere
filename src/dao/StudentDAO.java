
package dao;

import java.util.ArrayList;
import model.Student;

public class StudentDAO {

    // List to store student objects
    final private ArrayList<Student> students;

    // Constructor
    public StudentDAO() {

        students = new ArrayList<Student>();

    }

    // Add a new student
    public void addStudent(Student student) {

        students.add(student);

    }

    // Find student using roll number
    public Student findStudentByRollNumber(String rollNumber) {

        for (Student student : students) {

            if (student.getRollNumber().equals(rollNumber)) {
                return student;
            }

        }

        return null;

    }

    // Find student using email
    public Student findStudentByEmail(String email) {

        for (Student student : students) {

            if (student.getEmail().equalsIgnoreCase(email)) {
                return student;
            }

        }

        return null;

    }

    // Get all registered students
    public ArrayList<Student> getAllStudents() {

        return students;

    }

    // Check whether roll number already exists
    public boolean rollNumberExists(String rollNumber) {

        for (Student student : students) {

            if (student.getRollNumber().equals(rollNumber)) {
                return true;
            }

        }

        return false;

    }

    // Check whether email already exists
    public boolean emailExists(String email) {

        for (Student student : students) {

            if (student.getEmail().equalsIgnoreCase(email)) {
                return true;
            }

        }

        return false;

    }

    // Update student information
    public boolean updateStudent(Student updatedStudent) {

        for (int i = 0; i < students.size(); i++) {

            if (students.get(i).getRollNumber()
                    .equals(updatedStudent.getRollNumber())) {

                students.set(i, updatedStudent);

                return true;

            }

        }

        return false;

    }

    // Delete a student using roll number
    public boolean deleteStudentByRollNumber(String rollNumber) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getRollNumber().equalsIgnoreCase(rollNumber)) {
                students.remove(i);
                return true;
            }
        }
        return false;
    }

}
