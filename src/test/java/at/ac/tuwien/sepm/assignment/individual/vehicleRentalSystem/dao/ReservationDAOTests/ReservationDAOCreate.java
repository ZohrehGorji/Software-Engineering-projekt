package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.ReservationDAOTests;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Util.DButil;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.ReservationDAOJDBC;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import static junit.framework.TestCase.assertEquals;

public class ReservationDAOCreate { private static Connection connection;
    private static ReservationDAOJDBC ReservationDAO;
    private static Reservation reservation = new Reservation("maja", "CH0204835000626882001", Timestamp.valueOf("2031-04-11 13:23:44"), Timestamp.valueOf("2031-04-11 15:23:44"), 31, 20, "open");

    @BeforeClass
    public static void beforeMethode() throws SQLException {
        connection = DButil.getConnection();
        connection.setAutoCommit(false);
        ReservationDAO = new ReservationDAOJDBC();

    }
    @Test(expected = IllegalArgumentException.class)
    public void createReservationWithNullValue() throws DAOException {
        ReservationDAO.createReservation(null);
    }
    @Test
    public void saveInDatabase() throws DAOException {

        ReservationDAO.createReservation(reservation);
        int size = ReservationDAO.getInvoices().size();
        ReservationDAO.createReservation(reservation);
        assertEquals(size + 1, ReservationDAO.getInvoices().size());
    }
}
