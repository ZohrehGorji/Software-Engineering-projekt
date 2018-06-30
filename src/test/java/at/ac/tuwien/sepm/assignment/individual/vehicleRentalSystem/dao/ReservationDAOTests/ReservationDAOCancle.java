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
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ReservationDAOCancle { private static Connection connection;
    private static ReservationDAOJDBC ReservationDAO;
    private static Reservation res = new Reservation("maja", "CH0204835000626882001", Timestamp.valueOf("2040-04-11 13:23:44"), Timestamp.valueOf("2040-04-11 15:23:44"), 28, 20, "open");


    @BeforeClass
    public static void beforeMethode() throws SQLException {
        connection = DButil.getConnection();
        connection.setAutoCommit(false);

        ReservationDAO = new ReservationDAOJDBC();
    }
    @Test
    public void cancle() throws DAOException {
       ReservationDAO.createReservation(res);
        assertEquals("open",ReservationDAO.getInvoices().get(0).getStatus());
        System.out.println(ReservationDAO.getInvoices().get(0).toString());

        ReservationDAO.cancleONid(res.getReservation_id());
        assertEquals("canceled",ReservationDAO.getInvoices().get(0).getStatus());
        System.out.println(ReservationDAO.getInvoices().get(0).toString());

        ReservationDAO.cancleReservation(res);
    }

}
