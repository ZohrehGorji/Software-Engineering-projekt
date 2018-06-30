package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleServiceTests;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Util.DButil;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleService;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleServiceImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

public class VehicleServiceTestDelete {

    private static VehicleService vehicleService = new VehicleServiceImpl();
    private static Vehicle vehicle = new Vehicle("bmw", 1990, "old", 4, "hbqjhwr6243", "motorized", 30, 14, null, "A");
    private static Connection connection;


    @BeforeClass
    public static void beforeMethode() throws SQLException {
        connection = DButil.getConnection();
        connection.setAutoCommit(false);
        vehicleService = new VehicleServiceImpl();
    }
    @Test
    public void delete() throws  NotFoundException, ServiceException {

        int size = vehicleService.getVehicles().size();
        vehicleService.createVehicle(vehicle);
        assertEquals(size + 1, vehicleService.getVehicles().size());
        int size2 = vehicleService.getVehicles().size();
        vehicleService.delete(vehicle);
        assertEquals(size2-1, vehicleService.getVehicles().size());
    }
    @After
    public void doRollback() throws SQLException {
        connection.rollback();
    }

    @AfterClass
    public static void connectionClosed() throws SQLException {
        connection.close();

    }
}
