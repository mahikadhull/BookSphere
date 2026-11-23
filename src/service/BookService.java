package service;

import dao.BookDAO;
import exception.BookNotFoundException;
import exception.DataAccessException;
import model.Book;

import java.util.List;

public class BookService {

    private final BookDAO bookDAO;

    // Constructor
    public BookService() {
        this.bookDAO = new BookDAO();
    }

    // Add a new book
    public boolean addBook(Book book)
            throws DataAccessException {

        validateBook(book);

        return bookDAO.addBook(book);
    }

    // Get all books
    public List<Book> getAllBooks()
            throws DataAccessException {

        return bookDAO.getAllBooks();
    }

    // Get book using ID
    public Book getBookById(String bookId)
            throws BookNotFoundException, DataAccessException {

        Book book = bookDAO.findBookById(bookId);

        if (book == null) {

            throw new BookNotFoundException(
                    "Book not found with ID: " + bookId
            );
        }

        return book;
    }

    // Search books
    public List<Book> searchBooks(String keyword)
            throws DataAccessException {

        return bookDAO.searchBooks(keyword);
    }

    // Update book details
    public boolean updateBook(Book book)
            throws BookNotFoundException, DataAccessException {

        validateBook(book);

        if (bookDAO.findBookById(book.getBookId()) == null) {

            throw new BookNotFoundException(
                    "Cannot update. Book not found with ID: "
                    + book.getBookId()
            );
        }

        return bookDAO.updateBook(book);
    }

    // Remove book
    public boolean removeBook(String bookId)
            throws BookNotFoundException, DataAccessException {

        if (bookDAO.findBookById(bookId) == null) {

            throw new BookNotFoundException(
                    "Cannot remove. Book not found: " + bookId
            );
        }

        return bookDAO.deleteBook(bookId);
    }

    // Validate book information
    private void validateBook(Book book) {

        if (book == null) {
            throw new IllegalArgumentException(
                    "Book cannot be null."
            );
        }

        if (isBlank(book.getBookId())
                || isBlank(book.getTitle())
                || isBlank(book.getAuthor())
                || isBlank(book.getIsbn())) {

            throw new IllegalArgumentException(
                    "Book ID, title, author and ISBN are required."
            );
        }
    }

    // Check empty or null strings
    private boolean isBlank(String value) {

        return value == null || value.trim().isEmpty();
    }
}
