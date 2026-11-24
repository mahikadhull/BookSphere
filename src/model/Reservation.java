package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    // Reservation status constants
    public static final String PENDING   = "PENDING";
    public static final String FULFILLED = "FULFILLED"; // book became available and loan was created
    public static final String CANCELLED = "CANCELLED";
    public static final String EXPIRED   = "EXPIRED";

    // Number of days a reservation stays active before it expires
    public static final int RESERVATION_EXPIRY_DAYS = 7;

    private String reservationId;
    private String studentId;   // references Student.userId
    private String bookId;      // references Book.bookId
    private LocalDate reservationDate;
    private LocalDate expiryDate;
    private String status;

    // Constructor
    public Reservation(String reservationId, String studentId,
                       String bookId, LocalDate reservationDate) {

        this.reservationId   = reservationId;
        this.studentId       = studentId;
        this.bookId          = bookId;
        this.reservationDate = reservationDate;
        this.expiryDate      = reservationDate.plusDays(RESERVATION_EXPIRY_DAYS);
        this.status          = PENDING;
    }

    // ── Getters ──────────────────────────────────────────────

    public String getReservationId() {
        return reservationId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getBookId() {
        return bookId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getStatus() {
        return status;
    }

    // ── Setters ──────────────────────────────────────────────

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ── Convenience helpers ───────────────────────────────────

    // Check whether the reservation is still pending
    public boolean isPending() {
        return PENDING.equalsIgnoreCase(status);
    }

    // Check whether the reservation has expired
    public boolean isExpired() {
        return isPending() && LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public String toString() {
        return "Reservation ID  : " + reservationId
             + "\nStudent ID      : " + studentId
             + "\nBook ID         : " + bookId
             + "\nReservation Date: " + reservationDate
             + "\nExpiry Date     : " + expiryDate
             + "\nStatus          : " + status;
    }
}
