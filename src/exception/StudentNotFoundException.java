
package exception;

public class StudentNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    // Constructor with message
    public StudentNotFoundException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
