package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationServiceTests;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Util.DButil;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationServieceImpl;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationsService;

import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import static junit.framework.TestCase.assertEquals;

public class ReservationServiceTestCancle {

    private static ReservationsService reservationsService;
    private static Reservation reservation = new Reservation("maja", "CH0204835000626882001", Timestamp.valueOf("2026-03-11 13:23:44"), Timestamp.valueOf("2026-03-11 15:23:44"), 23, 20, "open");
    private static Connection connection;


    @BeforeClass
    public static void beforeMethode() throws SQLException {
        connection = DButil.getConnection();
        connection.setAutoCommit(false);
        reservationsService = new ReservationServieceImpl();
    }
    @Test
    public void cancle() throws ServiceException, NotFoundException {
        reservationsService.saveReservation(reservation);
        Reservation r=reservationsService.getOneInvoice(reservation.getReservation_id());
        assertEquals("open",r.getStatus());
        System.out.println(r.toString());
        reservationsService.cancleONid(reservation.getReservation_id());
        Reservation r2=reservationsService.getOneInvoice(reservation.getReservation_id());
        assertEquals("canceled",r2.getStatus());
        System.out.println(r.toString());
   reservationsService.cancle(reservation);
    }

}
