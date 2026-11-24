package exception;

public class BookUnavailableException extends Exception {

    private static final long serialVersionUID = 1L;

    // Constructor with message
    public BookUnavailableException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public BookUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
