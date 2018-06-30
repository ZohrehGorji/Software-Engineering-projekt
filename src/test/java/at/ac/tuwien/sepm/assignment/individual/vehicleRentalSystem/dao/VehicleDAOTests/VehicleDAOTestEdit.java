package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.VehicleDAOTests;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Util.DButil;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.VehicleDAOJDBC;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

public class VehicleDAOTestEdit {
    private static Connection connection;
    private static VehicleDAOJDBC vehicleDAO;
    private static Vehicle vehicle = new Vehicle("bmw", 1990, "old", 4, "hbqjhwr6243", "motorized", 30, 14, null, "A");

    @BeforeClass
    public static void beforeMethode() throws SQLException {
        connection = DButil.getConnection();
        connection.setAutoCommit(false);
        vehicleDAO = new VehicleDAOJDBC();
    }
    @Test
    public void editVehicle() throws DAOException, NotFoundException {
        Vehicle vehicle2 = new Vehicle("bmw", 1990, "old", 4, "hbqjhwr6243", "motorized", 30, 14, null, "A");

        vehicleDAO.create(vehicle2);
        vehicle2.setDescription("one");
        vehicleDAO.edit(vehicle2);
        assertEquals("one", vehicle2.getDescription());
    }


}
