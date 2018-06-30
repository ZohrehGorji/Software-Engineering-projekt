package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.VehicleDAOTests;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Util.DButil;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.VehicleDAOJDBC;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

public class VehicleDAOTestCreate {
    private static Connection connection;
    private static VehicleDAOJDBC vehicleDAO;
    private static Vehicle vehicle = new Vehicle("bmw", 1990, "old", 4, "hbqjhwr6243", "motorized", 30, 14, null, "A");

    @BeforeClass
    public static void beforeMethode() throws SQLException {
        connection = DButil.getConnection();
        connection.setAutoCommit(false);
        vehicleDAO = new VehicleDAOJDBC();
    }
    @Test(expected = IllegalArgumentException.class)
    public void createVehicleWithNullValue() throws DAOException {
        vehicleDAO.create(null);
    }



    @Test
    public void saveInDatabase() throws DAOException {

        vehicleDAO.create(vehicle);
        int size = vehicleDAO.getAll().size();
        vehicleDAO.create(vehicle);
        assertEquals(size + 1, vehicleDAO.getAll().size());
    }

}
