package exception;

public class BookNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    // Constructor
    public BookNotFoundException(String message) {
        super(message);
    }
}
