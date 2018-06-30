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

public class ReservationServiceEdit {
    private static Connection connection;
    private static ReservationsService reservationsService;
    private static Reservation reservation ;
    @BeforeClass
    public static void beforeMethode() throws SQLException {
        connection = DButil.getConnection();
        connection.setAutoCommit(false);
        reservationsService = new ReservationServieceImpl();

    }

    @Test
    public void editReservation() throws ServiceException, NotFoundException {
        reservation= new Reservation("maja", "CH0204835000626882001", Timestamp.valueOf("2029-01-11 13:23:44"), Timestamp.valueOf("2029-01-11 15:23:44"), 22, 20, "open");
        int bookingID = reservationsService.getBookingID();
        bookingID++;
        reservationsService.saveReservationonID(reservation, bookingID);
        reservation.setReservation_secondID(bookingID);
        reservation.setClientName("sara");
        reservationsService.editReservation(reservation);
        Reservation r=reservationsService.getOneInvoice(reservation.getReservation_id());
        assertEquals(reservation.getClientName(),r.getClientName());

    }
}