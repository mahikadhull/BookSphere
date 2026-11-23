package dao;

import exception.DataAccessException;
import model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private static final String FILE_NAME = "books.dat";

    private final FileStorageDAO fileStorageDAO;

    // Constructor
    public BookDAO() {
        this.fileStorageDAO = new FileStorageDAO();
    }

    // Retrieve all books from file
    @SuppressWarnings("unchecked")
    public List<Book> getAllBooks()
            throws DataAccessException {

        // If file does not exist, return empty list
        if (!fileStorageDAO.fileExists(FILE_NAME)) {
            return new ArrayList<>();
        }

        List<?> savedBooks =
                fileStorageDAO.loadData(FILE_NAME, List.class);

        return new ArrayList<>((List<Book>) savedBooks);
    }

    // Add a new book
    public boolean addBook(Book book)
            throws DataAccessException {

        if (book == null || findBookById(book.getBookId()) != null) {
            return false;
        }

        List<Book> books = getAllBooks();

        books.add(book);

        saveBooks(books);

        return true;
    }

    // Find book using ID
    public Book findBookById(String bookId)
            throws DataAccessException {

        for (Book book : getAllBooks()) {

            if (book.getBookId().equalsIgnoreCase(bookId)) {
                return book;
            }
        }

        return null;
    }

    // Search by title, author or ISBN
    public List<Book> searchBooks(String keyword)
            throws DataAccessException {

        List<Book> matches = new ArrayList<>();

        String searchText = (keyword == null)
                ? "" : keyword.trim().toLowerCase();

        for (Book book : getAllBooks()) {

            boolean titleMatches =
                    book.getTitle() != null
                    && book.getTitle().toLowerCase()
                    .contains(searchText);

            boolean authorMatches =
                    book.getAuthor() != null
                    && book.getAuthor().toLowerCase()
                    .contains(searchText);

            boolean isbnMatches =
                    book.getIsbn() != null
                    && book.getIsbn().toLowerCase()
                    .contains(searchText);

            if (titleMatches || authorMatches || isbnMatches) {
                matches.add(book);
            }
        }

        return matches;
    }

    // Update existing book
    public boolean updateBook(Book updatedBook)
            throws DataAccessException {

        if (updatedBook == null) {
            return false;
        }

        List<Book> books = getAllBooks();

        for (int i = 0; i < books.size(); i++) {

            if (books.get(i).getBookId()
                    .equalsIgnoreCase(updatedBook.getBookId())) {

                books.set(i, updatedBook);

                saveBooks(books);

                return true;
            }
        }

        return false;
    }

    // Delete a book
    public boolean deleteBook(String bookId)
            throws DataAccessException {

        List<Book> books = getAllBooks();

        boolean removed = books.removeIf(
                book -> book.getBookId().equalsIgnoreCase(bookId)
        );

        if (removed) {
            saveBooks(books);
        }

        return removed;
    }

    // Save the updated list
    private void saveBooks(List<Book> books)
            throws DataAccessException {

        fileStorageDAO.saveData(
                FILE_NAME,
                new ArrayList<Book>(books)
        );
    }
}
