package dao;

import exception.DataAccessException;
import model.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    private static final String FILE_NAME = "loans.dat";

    private final FileStorageDAO fileStorageDAO;

    // Constructor
    public LoanDAO() {
        this.fileStorageDAO = new FileStorageDAO();
    }

    // Retrieve all loans from file
    @SuppressWarnings("unchecked")
    public List<Loan> getAllLoans()
            throws DataAccessException {

        if (!fileStorageDAO.fileExists(FILE_NAME)) {
            return new ArrayList<>();
        }

        List<?> saved = fileStorageDAO.loadData(FILE_NAME, List.class);

        return new ArrayList<>((List<Loan>) saved);
    }

    // Save a new loan
    public boolean saveLoan(Loan loan)
            throws DataAccessException {

        if (loan == null || findLoanById(loan.getLoanId()) != null) {
            return false;
        }

        List<Loan> loans = getAllLoans();
        loans.add(loan);
        saveLoans(loans);

        return true;
    }

    // Find a loan by its ID
    public Loan findLoanById(String loanId)
            throws DataAccessException {

        for (Loan loan : getAllLoans()) {
            if (loan.getLoanId().equalsIgnoreCase(loanId)) {
                return loan;
            }
        }

        return null;
    }

    // Get all active loans for a specific student
    public List<Loan> findActiveLoansByStudent(String studentId)
            throws DataAccessException {

        List<Loan> result = new ArrayList<>();

        for (Loan loan : getAllLoans()) {
            if (loan.getStudentId().equalsIgnoreCase(studentId)
                    && loan.isActive()) {
                result.add(loan);
            }
        }

        return result;
    }

    // Get all loans (active and historical) for a specific student
    public List<Loan> findLoansByStudent(String studentId)
            throws DataAccessException {

        List<Loan> result = new ArrayList<>();

        for (Loan loan : getAllLoans()) {
            if (loan.getStudentId().equalsIgnoreCase(studentId)) {
                result.add(loan);
            }
        }

        return result;
    }

    // Get the active loan for a specific book copy (at most one at a time)
    public Loan findActiveLoanByCopyId(String copyId)
            throws DataAccessException {

        for (Loan loan : getAllLoans()) {
            if (loan.getCopyId().equalsIgnoreCase(copyId)
                    && loan.isActive()) {
                return loan;
            }
        }

        return null;
    }

    // Get all overdue loans across all students
    public List<Loan> findAllOverdueLoans()
            throws DataAccessException {

        List<Loan> overdue = new ArrayList<>();

        for (Loan loan : getAllLoans()) {
            if (loan.isOverdue()) {
                overdue.add(loan);
            }
        }

        return overdue;
    }

    // Update an existing loan record (e.g., on return)
    public boolean updateLoan(Loan updatedLoan)
            throws DataAccessException {

        if (updatedLoan == null) {
            return false;
        }

        List<Loan> loans = getAllLoans();

        for (int i = 0; i < loans.size(); i++) {
            if (loans.get(i).getLoanId()
                    .equalsIgnoreCase(updatedLoan.getLoanId())) {
                loans.set(i, updatedLoan);
                saveLoans(loans);
                return true;
            }
        }

        return false;
    }

    // Persist the loan list to disk
    private void saveLoans(List<Loan> loans)
            throws DataAccessException {

        fileStorageDAO.saveData(FILE_NAME, new ArrayList<>(loans));
    }
}
