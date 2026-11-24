
package model;

import java.io.Serializable;

public class Student extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String rollNumber;
    private String department;
    private int academicYear;
    private int borrowedBookCount;

    // Default constructor
    public Student() {

        super();
        borrowedBookCount = 0;

    }

    // Parameterized constructor
    public Student(String userId, String name, String email,
                   String password, String rollNumber,
                   String department, int academicYear) {

        super(userId, name, email, password);

        this.rollNumber = rollNumber;
        this.department = department;
        this.academicYear = academicYear;
        this.borrowedBookCount = 0;

    }

    // Getters

    public String getRollNumber() {
        return rollNumber;
    }

    public String getDepartment() {
        return department;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public int getBorrowedBookCount() {
        return borrowedBookCount;
    }

    // Setters

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public void setBorrowedBookCount(int borrowedBookCount) {
        this.borrowedBookCount = borrowedBookCount;
    }

    // Increase borrowed book count
    public void borrowBook() {
        borrowedBookCount++;
    }

    // Decrease borrowed book count
    public void returnBook() {

        if (borrowedBookCount > 0) {
            borrowedBookCount--;
        }

    }

    // Method overriding
    @Override
    public String getRole() {
        return "Student";
    }

}
