package service;

import dao.BookDAO;
import dao.LoanDAO;
import dao.StudentDAO;
import exception.BookNotFoundException;
import exception.BookUnavailableException;
import exception.BorrowLimitExceededException;
import exception.DataAccessException;
import exception.StudentNotFoundException;
import model.Book;
import model.BookCopy;
import model.Loan;
import model.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CirculationService {

    private final LoanDAO    loanDAO;
    private final BookDAO    bookDAO;
    private final StudentDAO studentDAO;

    // Constructor
    public CirculationService() {
        this.loanDAO    = new LoanDAO();
        this.bookDAO    = new BookDAO();
        this.studentDAO = new StudentDAO();
    }

    // ── Borrow a book ─────────────────────────────────────────────────────────

    /**
     * Issues a book copy to a student.
     *
     * @param studentId the student's user ID
     * @param bookId    the book to be borrowed
     * @return the created Loan record
     */
    public Loan borrowBook(String studentId, String bookId)
            throws StudentNotFoundException, BookNotFoundException,
                   BookUnavailableException, BorrowLimitExceededException,
                   DataAccessException {

        // 1. Validate student
        Student student = studentDAO.findStudentByEmail(studentId);
        if (student == null) {
            // Try by userId as well
            throw new StudentNotFoundException(
                    "No student found with ID: " + studentId);
        }

        // 2. Enforce borrow limit
        List<Loan> activeLoans = loanDAO.findActiveLoansByStudent(studentId);
        if (activeLoans.size() >= Loan.MAX_BORROW_LIMIT) {
            throw new BorrowLimitExceededException(
                    "Student " + studentId + " has reached the maximum borrow"
                    + " limit of " + Loan.MAX_BORROW_LIMIT + " books.");
        }

        // 3. Validate book and find an available copy
        Book book = bookDAO.findBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException(
                    "Book not found with ID: " + bookId);
        }

        BookCopy availableCopy = null;
        for (BookCopy copy : book.getCopies()) {
            if (copy.isAvailable()) {
                availableCopy = copy;
                break;
            }
        }

        if (availableCopy == null) {
            throw new BookUnavailableException(
                    "No available copy for book: " + book.getTitle()
                    + " (ID: " + bookId + ").");
        }

        // 4. Mark copy as ISSUED
        availableCopy.setStatus(BookCopy.ISSUED);
        bookDAO.updateBook(book);

        // 5. Update student's borrow count
        student.borrowBook();
        studentDAO.updateStudent(student);

        // 6. Create and persist loan record
        String loanId = "LN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Loan loan = new Loan(
                loanId,
                studentId,
                bookId,
                availableCopy.getCopyId(),
                LocalDate.now()
        );

        loanDAO.saveLoan(loan);

        return loan;
    }

    // ── Return a book ─────────────────────────────────────────────────────────

    /**
     * Processes the return of a borrowed book.
     *
     * @param loanId the ID of the loan to close
     * @return the updated Loan record
     */
    public Loan returnBook(String loanId)
            throws BookNotFoundException, DataAccessException {

        // 1. Find the loan
        Loan loan = loanDAO.findLoanById(loanId);
        if (loan == null || !loan.isActive()) {
            throw new IllegalArgumentException(
                    "No active loan found with ID: " + loanId);
        }

        // 2. Mark loan as returned
        loan.setReturnDate(LocalDate.now());
        loan.setStatus(Loan.RETURNED);
        loanDAO.updateLoan(loan);

        // 3. Mark copy as AVAILABLE again
        Book book = bookDAO.findBookById(loan.getBookId());
        if (book != null) {
            for (BookCopy copy : book.getCopies()) {
                if (copy.getCopyId().equalsIgnoreCase(loan.getCopyId())) {
                    copy.setStatus(BookCopy.AVAILABLE);
                    break;
                }
            }
            bookDAO.updateBook(book);
        }

        // 4. Update student's borrow count
        Student student = studentDAO.findStudentByEmail(loan.getStudentId());
        if (student != null) {
            student.returnBook();
            studentDAO.updateStudent(student);
        }

        return loan;
    }

    // ── Loan queries ──────────────────────────────────────────────────────────

    // Get all loans for a student
    public List<Loan> getLoansByStudent(String studentId)
            throws DataAccessException {
        return loanDAO.findLoansByStudent(studentId);
    }

    // Get active loans for a student
    public List<Loan> getActiveLoansByStudent(String studentId)
            throws DataAccessException {
        return loanDAO.findActiveLoansByStudent(studentId);
    }

    // Get all overdue loans in the system
    public List<Loan> getOverdueLoans()
            throws DataAccessException {

        List<Loan> all = loanDAO.getAllLoans();

        // Sync OVERDUE status before returning
        for (Loan loan : all) {
            if (loan.isOverdue() && Loan.ACTIVE.equalsIgnoreCase(loan.getStatus())) {
                loan.setStatus(Loan.OVERDUE);
                loanDAO.updateLoan(loan);
            }
        }

        return loanDAO.findAllOverdueLoans();
    }

    // Get a loan by its ID
    public Loan getLoanById(String loanId)
            throws DataAccessException {
        return loanDAO.findLoanById(loanId);
    }

    // Get all loans in the system
    public List<Loan> getAllLoans()
            throws DataAccessException {
        return loanDAO.getAllLoans();
    }
}
