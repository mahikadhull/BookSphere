package model;

import java.io.Serializable;

public class BookCopy implements Serializable {

    private static final long serialVersionUID = 1L;

    // Copy status constants
    public static final String AVAILABLE = "AVAILABLE";
    public static final String ISSUED = "ISSUED";
    public static final String RESERVED = "RESERVED";

    private String copyId;
    private String status;

    // Constructor: new copy is available by default
    public BookCopy(String copyId) {
        this.copyId = copyId;
        this.status = AVAILABLE;
    }

    // Constructor with status
    public BookCopy(String copyId, String status) {
        this.copyId = copyId;
        this.status = status;
    }

    // Getters and Setters
    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Check whether this copy is available
    public boolean isAvailable() {
        return AVAILABLE.equalsIgnoreCase(status);
    }

    @Override
    public String toString() {
        return "Copy ID: " + copyId
                + " | Status: " + status;
    }
}
