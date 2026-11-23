
package model;

import java.io.Serializable;

public class User implements Serializable {

    private String userId;
    private String name;
    private String email;
    private String password;

    // Default constructor
    public User() {

    }

    // Parameterized constructor
    public User(String userId, String name, String email, String password) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;

    }

    // Getters

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Method to identify user type
    public String getRole() {
        return "User";
    }
}
