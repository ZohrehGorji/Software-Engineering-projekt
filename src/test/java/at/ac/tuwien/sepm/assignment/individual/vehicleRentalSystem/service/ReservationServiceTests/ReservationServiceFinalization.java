package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationServiceTests;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Util.DButil;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.ReservationDAOJDBC;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationServieceImpl;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationsService;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleService;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import static junit.framework.TestCase.assertEquals;

public class ReservationServiceFinalization {
    private static Connection connection;
    private static ReservationsService reservationsService;
    private static Reservation reservation = new Reservation("maja", "CH0204835000626882001", Timestamp.valueOf("2026-11-11 13:23:44"), Timestamp.valueOf("2026-11-11 15:23:44"), 24, 20, "open");

    @BeforeClass
    public static void beforeMethode() throws SQLException {
        connection = DButil.getConnection();
        connection.setAutoCommit(false);
        reservationsService = new ReservationServieceImpl();

    }

    @Test
    public void finalizedReservation() throws  ServiceException, NotFoundException {
        int bookingID = reservationsService.getBookingID();
        bookingID++;
        reservationsService.saveReservationonID(reservation, bookingID);
        Reservation r1=reservationsService.getOneInvoice(reservation.getReservation_id());
        assertEquals("open",r1.getStatus());
        reservation.setReservation_secondID(bookingID);
        reservationsService.finalizeReservation(reservation);
        Reservation r=reservationsService.getOneInvoice(reservation.getReservation_id());
       assertEquals("closed",r.getStatus());

    }

}
