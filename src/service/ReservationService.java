package service;

import dao.BookDAO;
import dao.ReservationDAO;
import dao.StudentDAO;
import exception.BookNotFoundException;
import exception.BookUnavailableException;
import exception.DataAccessException;
import exception.StudentNotFoundException;
import model.Book;
import model.Reservation;
import model.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ReservationService {

    private final ReservationDAO reservationDAO;
    private final BookDAO        bookDAO;
    private final StudentDAO     studentDAO;

    // Constructor
    public ReservationService() {
        this.reservationDAO = new ReservationDAO();
        this.bookDAO        = new BookDAO();
        this.studentDAO     = new StudentDAO();
    }

    // ── Place a reservation ───────────────────────────────────────────────────

    /**
     * Places a hold/reservation on a book for a student.
     * A reservation is only allowed when no copy is currently available
     * (otherwise the student should simply borrow it).
     *
     * @param studentId the student's user ID
     * @param bookId    the book to reserve
     * @return the created Reservation record
     */
    public Reservation placeReservation(String studentId, String bookId)
            throws StudentNotFoundException, BookNotFoundException,
                   BookUnavailableException, DataAccessException {

        // 1. Validate student
        Student student = studentDAO.findStudentByEmail(studentId);
        if (student == null) {
            throw new StudentNotFoundException(
                    "No student found with ID: " + studentId);
        }

        // 2. Validate book
        Book book = bookDAO.findBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException(
                    "Book not found with ID: " + bookId);
        }

        // 3. Reject duplicate pending reservation
        if (reservationDAO.hasPendingReservation(studentId, bookId)) {
            throw new IllegalStateException(
                    "Student " + studentId
                    + " already has a pending reservation for book: "
                    + bookId);
        }

        // 4. If book is available right now, the student should borrow, not reserve
        if (book.getAvailableCopies() > 0) {
            throw new BookUnavailableException(
                    "Book '" + book.getTitle()
                    + "' has available copies. Please borrow it instead of reserving.");
        }

        // 5. Create and persist reservation
        String reservationId =
                "RV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Reservation reservation = new Reservation(
                reservationId,
                studentId,
                bookId,
                LocalDate.now()
        );

        reservationDAO.saveReservation(reservation);

        return reservation;
    }

    // ── Cancel a reservation ──────────────────────────────────────────────────

    /**
     * Cancels a pending reservation.
     *
     * @param reservationId the ID of the reservation to cancel
     * @return true if cancelled successfully
     */
    public boolean cancelReservation(String reservationId)
            throws DataAccessException {

        Reservation reservation =
                reservationDAO.findReservationById(reservationId);

        if (reservation == null) {
            throw new IllegalArgumentException(
                    "No reservation found with ID: " + reservationId);
        }

        if (!reservation.isPending()) {
            throw new IllegalStateException(
                    "Reservation " + reservationId
                    + " cannot be cancelled (status: " + reservation.getStatus() + ").");
        }

        reservation.setStatus(Reservation.CANCELLED);
        return reservationDAO.updateReservation(reservation);
    }

    // ── Fulfil a reservation ──────────────────────────────────────────────────

    /**
     * Marks a reservation as FULFILLED (called internally when a returned
     * book satisfies the next waiting reservation).
     *
     * @param reservationId the ID of the reservation to fulfil
     * @return true if updated successfully
     */
    public boolean fulfillReservation(String reservationId)
            throws DataAccessException {

        Reservation reservation =
                reservationDAO.findReservationById(reservationId);

        if (reservation == null || !reservation.isPending()) {
            return false;
        }

        reservation.setStatus(Reservation.FULFILLED);
        return reservationDAO.updateReservation(reservation);
    }

    // ── Expire stale reservations ─────────────────────────────────────────────

    /**
     * Scans all PENDING reservations and marks expired ones as EXPIRED.
     *
     * @return the number of reservations that were expired
     */
    public int expireOldReservations()
            throws DataAccessException {

        int count = 0;

        for (Reservation reservation : reservationDAO.getAllReservations()) {
            if (reservation.isExpired()) {
                reservation.setStatus(Reservation.EXPIRED);
                reservationDAO.updateReservation(reservation);
                count++;
            }
        }

        return count;
    }

    // ── Queries ───────────────────────────────────────────────────────────────

    // Get all reservations for a student
    public List<Reservation> getReservationsByStudent(String studentId)
            throws DataAccessException {
        return reservationDAO.findReservationsByStudent(studentId);
    }

    // Get pending reservations for a student
    public List<Reservation> getPendingReservationsByStudent(String studentId)
            throws DataAccessException {
        return reservationDAO.findPendingReservationsByStudent(studentId);
    }

    // Get all pending reservations for a specific book (waiting queue)
    public List<Reservation> getPendingReservationsByBook(String bookId)
            throws DataAccessException {
        return reservationDAO.findPendingReservationsByBook(bookId);
    }

    // Get a reservation by its ID
    public Reservation getReservationById(String reservationId)
            throws DataAccessException {
        return reservationDAO.findReservationById(reservationId);
    }

    // Get all reservations in the system
    public List<Reservation> getAllReservations()
            throws DataAccessException {
        return reservationDAO.getAllReservations();
    }
}
