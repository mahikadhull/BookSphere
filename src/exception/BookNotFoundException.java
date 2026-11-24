package exception;

public class BookNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    // Constructor with message
    public BookNotFoundException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
