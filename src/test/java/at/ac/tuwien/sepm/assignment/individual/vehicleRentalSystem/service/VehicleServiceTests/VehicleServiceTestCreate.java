package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleServiceTests;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Util.DButil;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.VehicleDAOJDBC;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleService;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleServiceImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

public class VehicleServiceTestCreate {
    private static VehicleService vehicleService = new VehicleServiceImpl();
    private static Vehicle vehicle = new Vehicle("bmw", 1990, "old", 4, "hbqjhwr6243", "motorized", 30, 14, null, "A");
    private static Vehicle v2 = new Vehicle("benz", 2018, "new", 4, "MHG5736", "motorized", 300, 67, null, "B");
    private static Vehicle v3 = new Vehicle("fiat", 2011, "new", 2, "MHD8645", "motorized", 343, 67, null, "B");

    private static Connection connection;


    @BeforeClass
    public static void beforeMethode() throws SQLException {
        connection = DButil.getConnection();
        connection.setAutoCommit(false);
        vehicleService = new VehicleServiceImpl();
    }
    @Test(expected = ServiceException.class)
    public void createReservationWithNullValue() throws  ServiceException {
        vehicleService.createVehicle(null);
    }
    @Test(expected = ExceptionInInitializerError.class)
    public void createFalseValue() throws  ServiceException {
        vehicleService.createVehicle(new Vehicle("fiat", 2011, "new", 2, "MHD8645", "motorized", 343, -50, null, "B"));
    }
    @Test
    public void saveInDatabase() throws ServiceException, NotFoundException {
        vehicleService.createVehicle(v2);
        vehicleService.createVehicle(vehicle);
        vehicleService.createVehicle(v3);
        int size = vehicleService.getVehicles().size();
        vehicleService.createVehicle(vehicle);
        assertEquals(size + 1, vehicleService.getVehicles().size());
    }


}
