
package model;

public class Librarian extends User {

    private String employeeId;
    private String designation;

    // Default constructor
    public Librarian() {

        super();

    }

    // Parameterized constructor
    public Librarian(String userId, String name, String email,
                     String password, String employeeId,
                     String designation) {

        super(userId, name, email, password);

        this.employeeId = employeeId;
        this.designation = designation;

    }

    // Getters

    public String getEmployeeId() {
        return employeeId;
    }

    public String getDesignation() {
        return designation;
    }

    // Setters

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    // Method overriding
    @Override
    public String getRole() {
        return "Librarian";
    }

}
