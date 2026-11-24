package exception;

public class BorrowLimitExceededException extends Exception {

    private static final long serialVersionUID = 1L;

    // Constructor with message
    public BorrowLimitExceededException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public BorrowLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
