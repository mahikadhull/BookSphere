package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private int publicationYear;

    private Category category;
    private ShelfLocation shelfLocation;

    private List<BookCopy> copies;

    // Constructor
    public Book(String bookId, String title, String author,
                String isbn, String publisher, int publicationYear,
                Category category, ShelfLocation shelfLocation) {

        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publicationYear = publicationYear;

        this.category = category;
        this.shelfLocation = shelfLocation;

        // Initially, the book has no physical copies
        this.copies = new ArrayList<>();
    }

    // Getters and Setters
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ShelfLocation getShelfLocation() {
        return shelfLocation;
    }

    public void setShelfLocation(ShelfLocation shelfLocation) {
        this.shelfLocation = shelfLocation;
    }

    public List<BookCopy> getCopies() {
        return copies;
    }

    public void setCopies(List<BookCopy> copies) {
        if (copies == null) {
            this.copies = new ArrayList<>();
        } else {
            this.copies = copies;
        }
    }

    // Add a physical copy
    public void addCopy(BookCopy copy) {
        if (copy != null) {
            copies.add(copy);
        }
    }

    // Return total number of copies
    public int getTotalCopies() {
        return copies.size();
    }

    // Count available copies
    public int getAvailableCopies() {

        int count = 0;

        for (BookCopy copy : copies) {

            if (copy.isAvailable()) {
                count++;
            }
        }

        return count;
    }

    // Display book details
    @Override
    public String toString() {

        String categoryName = (category == null)
                ? "N/A" : category.getCategoryName();

        String shelf = (shelfLocation == null)
                ? "N/A" : shelfLocation.toString();

        return "Book ID: " + bookId
                + "\nTitle: " + title
                + "\nAuthor: " + author
                + "\nISBN: " + isbn
                + "\nPublisher: " + publisher
                + "\nPublication Year: " + publicationYear
                + "\nCategory: " + categoryName
                + "\nShelf Location: " + shelf
                + "\nTotal Copies: " + getTotalCopies()
                + "\nAvailable Copies: " + getAvailableCopies();
    }
}
