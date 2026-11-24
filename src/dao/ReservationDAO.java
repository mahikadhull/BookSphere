package dao;

import exception.DataAccessException;
import model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    private static final String FILE_NAME = "reservations.dat";

    private final FileStorageDAO fileStorageDAO;

    // Constructor
    public ReservationDAO() {
        this.fileStorageDAO = new FileStorageDAO();
    }

    // Retrieve all reservations from file
    @SuppressWarnings("unchecked")
    public List<Reservation> getAllReservations()
            throws DataAccessException {

        if (!fileStorageDAO.fileExists(FILE_NAME)) {
            return new ArrayList<>();
        }

        List<?> saved = fileStorageDAO.loadData(FILE_NAME, List.class);

        return new ArrayList<>((List<Reservation>) saved);
    }

    // Save a new reservation
    public boolean saveReservation(Reservation reservation)
            throws DataAccessException {

        if (reservation == null
                || findReservationById(reservation.getReservationId()) != null) {
            return false;
        }

        List<Reservation> reservations = getAllReservations();
        reservations.add(reservation);
        saveReservations(reservations);

        return true;
    }

    // Find a reservation by its ID
    public Reservation findReservationById(String reservationId)
            throws DataAccessException {

        for (Reservation reservation : getAllReservations()) {
            if (reservation.getReservationId()
                    .equalsIgnoreCase(reservationId)) {
                return reservation;
            }
        }

        return null;
    }

    // Get all pending reservations for a specific student
    public List<Reservation> findPendingReservationsByStudent(String studentId)
            throws DataAccessException {

        List<Reservation> result = new ArrayList<>();

        for (Reservation reservation : getAllReservations()) {
            if (reservation.getStudentId().equalsIgnoreCase(studentId)
                    && reservation.isPending()) {
                result.add(reservation);
            }
        }

        return result;
    }

    // Get all reservations for a specific student
    public List<Reservation> findReservationsByStudent(String studentId)
            throws DataAccessException {

        List<Reservation> result = new ArrayList<>();

        for (Reservation reservation : getAllReservations()) {
            if (reservation.getStudentId().equalsIgnoreCase(studentId)) {
                result.add(reservation);
            }
        }

        return result;
    }

    // Get all pending reservations for a specific book
    public List<Reservation> findPendingReservationsByBook(String bookId)
            throws DataAccessException {

        List<Reservation> result = new ArrayList<>();

        for (Reservation reservation : getAllReservations()) {
            if (reservation.getBookId().equalsIgnoreCase(bookId)
                    && reservation.isPending()) {
                result.add(reservation);
            }
        }

        return result;
    }

    // Check whether a student already has a pending reservation for a book
    public boolean hasPendingReservation(String studentId, String bookId)
            throws DataAccessException {

        for (Reservation reservation : getAllReservations()) {
            if (reservation.getStudentId().equalsIgnoreCase(studentId)
                    && reservation.getBookId().equalsIgnoreCase(bookId)
                    && reservation.isPending()) {
                return true;
            }
        }

        return false;
    }

    // Update an existing reservation (e.g., status change)
    public boolean updateReservation(Reservation updatedReservation)
            throws DataAccessException {

        if (updatedReservation == null) {
            return false;
        }

        List<Reservation> reservations = getAllReservations();

        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationId()
                    .equalsIgnoreCase(updatedReservation.getReservationId())) {
                reservations.set(i, updatedReservation);
                saveReservations(reservations);
                return true;
            }
        }

        return false;
    }

    // Persist the reservation list to disk
    private void saveReservations(List<Reservation> reservations)
            throws DataAccessException {

        fileStorageDAO.saveData(FILE_NAME, new ArrayList<>(reservations));
    }
}
