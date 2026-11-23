package service;

import dao.BookDAO;
import exception.BookNotFoundException;
import exception.DataAccessException;
import model.Book;
import model.BookCopy;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    private final BookDAO bookDAO;

    // Constructor
    public InventoryService() {
        this.bookDAO = new BookDAO();
    }

    // Add a physical copy of an existing book
    public boolean addBookCopy(String bookId, String copyId)
            throws BookNotFoundException, DataAccessException {

        Book book = findBook(bookId);

        if (isBlank(copyId)) {
            throw new IllegalArgumentException(
                    "Copy ID cannot be empty."
            );
        }

        // Check whether copy ID already exists
        for (BookCopy copy : book.getCopies()) {

            if (copy.getCopyId().equalsIgnoreCase(copyId)) {
                return false;
            }
        }

        // Create and add new copy
        book.addCopy(new BookCopy(copyId));

        return bookDAO.updateBook(book);
    }

    // Get all copies of a particular book
    public List<BookCopy> getCopiesByBookId(String bookId)
            throws BookNotFoundException, DataAccessException {

        return new ArrayList<>(findBook(bookId).getCopies());
    }

    // Count available copies
    public int getAvailableCopies(String bookId)
            throws BookNotFoundException, DataAccessException {

        return findBook(bookId).getAvailableCopies();
    }

    // Check book availability
    public boolean isBookAvailable(String bookId)
            throws BookNotFoundException, DataAccessException {

        return getAvailableCopies(bookId) > 0;
    }

    // Find first available physical copy
    public BookCopy getAvailableCopy(String bookId)
            throws BookNotFoundException, DataAccessException {

        for (BookCopy copy : findBook(bookId).getCopies()) {

            if (copy.isAvailable()) {
                return copy;
            }
        }

        return null;
    }

    // Update status of a physical copy
    public boolean updateCopyStatus(String copyId, String status)
            throws BookNotFoundException, DataAccessException {

        // Validate status
        if (!BookCopy.AVAILABLE.equalsIgnoreCase(status)
                && !BookCopy.ISSUED.equalsIgnoreCase(status)
                && !BookCopy.RESERVED.equalsIgnoreCase(status)) {

            throw new IllegalArgumentException(
                    "Invalid copy status: " + status
            );
        }

        // Search for copy in all books
        for (Book book : bookDAO.getAllBooks()) {

            for (BookCopy copy : book.getCopies()) {

                if (copy.getCopyId().equalsIgnoreCase(copyId)) {

                    copy.setStatus(status.toUpperCase());

                    return bookDAO.updateBook(book);
                }
            }
        }

        throw new BookNotFoundException(
                "No book copy found with ID: " + copyId
        );
    }

    // Find book using its ID
    private Book findBook(String bookId)
            throws BookNotFoundException, DataAccessException {

        Book book = bookDAO.findBookById(bookId);

        if (book == null) {

            throw new BookNotFoundException(
                    "Book not found with ID: " + bookId
            );
        }

        return book;
    }

    // Check empty or null strings
    private boolean isBlank(String value) {

        return value == null || value.trim().isEmpty();
    }
}
