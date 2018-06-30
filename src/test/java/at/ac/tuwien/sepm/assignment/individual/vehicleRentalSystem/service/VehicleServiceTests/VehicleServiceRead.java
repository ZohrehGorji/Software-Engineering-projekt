package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleServiceTests;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Util.DButil;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleService;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

public class VehicleServiceRead {
    private static Connection connection;
    private static VehicleService vehicleService;
    private static Vehicle vehicle = new Vehicle("bmw", 1990, "old", 4, "hbqjhwr6243", "motorized", 30, 14, null, "A");

    @BeforeClass
    public static void beforeMethode() throws SQLException {
        connection = DButil.getConnection();
        connection.setAutoCommit(false);
        vehicleService = new VehicleServiceImpl();
    }
    @Test
    public void readVehicle() throws ServiceException {

        vehicleService.createVehicle(vehicle);
        Vehicle v=vehicleService.getVehicleEvenIfIsDeleted(vehicle.getId());
        assertEquals(vehicle.getPrice(), v.getPrice());
        assertEquals(vehicle.getYear(), v.getYear());
        assertEquals(vehicle.getSeats(), v.getSeats());
        assertEquals(vehicle.getLicensePlate(), v.getLicensePlate());
        assertEquals(vehicle.getTypeOfDrive(), v.getTypeOfDrive());
        assertEquals(vehicle.getId(), vehicleService.getvID());

    }
}
