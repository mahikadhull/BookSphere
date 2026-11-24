package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;

    // Loan status constants
    public static final String ACTIVE   = "ACTIVE";
    public static final String RETURNED = "RETURNED";
    public static final String OVERDUE  = "OVERDUE";

    // Maximum number of books a student may borrow at once
    public static final int MAX_BORROW_LIMIT = 3;

    // Standard loan period in days
    public static final int LOAN_PERIOD_DAYS = 14;

    private String loanId;
    private String studentId;    // references Student.userId
    private String bookId;       // references Book.bookId
    private String copyId;       // references BookCopy.copyId
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate; // null until the book is returned
    private String status;

    // Constructor
    public Loan(String loanId, String studentId,
                String bookId, String copyId,
                LocalDate issueDate) {

        this.loanId     = loanId;
        this.studentId  = studentId;
        this.bookId     = bookId;
        this.copyId     = copyId;
        this.issueDate  = issueDate;
        this.dueDate    = issueDate.plusDays(LOAN_PERIOD_DAYS);
        this.returnDate = null;
        this.status     = ACTIVE;
    }

    // ── Getters ──────────────────────────────────────────────

    public String getLoanId() {
        return loanId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getCopyId() {
        return copyId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public String getStatus() {
        return status;
    }

    // ── Setters ──────────────────────────────────────────────

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ── Convenience helpers ───────────────────────────────────

    // Check whether this loan is currently active (not yet returned)
    public boolean isActive() {
        return ACTIVE.equalsIgnoreCase(status);
    }

    // Check whether the loan is overdue (past due date and not returned)
    public boolean isOverdue() {
        if (!isActive()) {
            return false;
        }
        return LocalDate.now().isAfter(dueDate);
    }

    // Calculate the number of days the loan is overdue (0 if not overdue)
    public long getOverdueDays() {
        if (!isOverdue()) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
    }

    @Override
    public String toString() {
        return "Loan ID    : " + loanId
             + "\nStudent ID : " + studentId
             + "\nBook ID    : " + bookId
             + "\nCopy ID    : " + copyId
             + "\nIssue Date : " + issueDate
             + "\nDue Date   : " + dueDate
             + "\nReturn Date: " + (returnDate != null ? returnDate : "Not returned")
             + "\nStatus     : " + status;
    }
}
