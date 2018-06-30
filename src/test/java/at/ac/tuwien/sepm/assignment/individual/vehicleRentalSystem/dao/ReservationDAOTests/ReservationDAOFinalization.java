package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.ReservationDAOTests;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Util.DButil;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.ReservationDAOJDBC;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class ReservationDAOFinalization {

    private static Connection connection;
    private static ReservationDAOJDBC ReservationDAO;
    private static Reservation reservation = new Reservation("maja", "CH0204835000626882001", Timestamp.valueOf("2033-04-11 13:23:44"), Timestamp.valueOf("2033-04-11 15:23:44"), 33, 20, "open");

    @BeforeClass
    public static void beforeMethode() throws SQLException {
        connection = DButil.getConnection();
        connection.setAutoCommit(false);
        ReservationDAO = new ReservationDAOJDBC();

    }

    @Test
    public void finalizedReservation() throws DAOException {
        int bookingID = ReservationDAO.getBookingID();
        bookingID++;
        ReservationDAO.createReservationonID(reservation, bookingID);
        assertEquals("open", ReservationDAO.getInvoices().get(0).getStatus());
        System.out.println(ReservationDAO.getInvoices().get(0).toString());
        reservation.setReservation_secondID(bookingID);
        ReservationDAO.finalizedReservation(reservation);
        assertEquals("closed", ReservationDAO.getInvoices().get(0).getStatus());
        System.out.println(ReservationDAO.getInvoices().get(0).toString());


    }


}