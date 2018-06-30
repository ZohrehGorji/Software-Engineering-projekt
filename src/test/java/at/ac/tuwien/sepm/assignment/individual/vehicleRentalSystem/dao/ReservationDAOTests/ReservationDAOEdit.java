package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.ReservationDAOTests;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Util.DButil;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.ReservationDAOJDBC;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import static junit.framework.TestCase.assertEquals;

public class ReservationDAOEdit {
    private static Connection connection;
    private static ReservationDAOJDBC ReservationDAO;
    private static Reservation reservation;
    @BeforeClass
    public static void beforeMethode() throws SQLException {
        connection = DButil.getConnection();
        connection.setAutoCommit(false);
        ReservationDAO = new ReservationDAOJDBC();

    }

    @Test
    public void editReservation() throws DAOException, NotFoundException {
        reservation = new Reservation("maja", "CH0204835000626882001", Timestamp.valueOf("2032-04-11 13:23:44"), Timestamp.valueOf("2032-04-11 15:23:44"), 32, 20, "open");
        int bookingID = ReservationDAO.getBookingID();
        System.out.println(bookingID);
        bookingID++;
        System.out.println(bookingID);
        ReservationDAO.createReservationonID(reservation, bookingID);
        reservation.setReservation_secondID(bookingID);
        System.out.println(reservation.getReservation_secondID());
        reservation.setClientName("sara");
        ReservationDAO.edit(reservation);
        assertEquals("sara", ReservationDAO.getInvoices().get(0).getClientName());


    }
}
