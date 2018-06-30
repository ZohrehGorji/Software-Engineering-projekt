package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.VehicleDAO;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.VehicleDAOJDBC;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the VehicleService, which impelents VehicleService and has implementation of all the methods send requests to DAO level.
 */
public class VehicleServiceImpl implements VehicleService {
    /**
     * LOG is The Logger of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * VehicleDAOTests is The instance of VehicleDAOJDBC.
     */
    private VehicleDAO VehicleDAO = new VehicleDAOJDBC();
    /**
     * vehicles is an ObservableList of vehicles.
     */
    private ObservableList<Vehicle> vehicles;

    @Override
    public void createVehicle(Vehicle v) throws ServiceException {
        LOG.debug("User want to create a Vehicle {} ", v);
        if (v==null){
            throw new ServiceException("Null Value");
        }
        try {
            VehicleValidation(v);
        } catch (ServiceException s) {
            AlertMaker alertMaker = new AlertMaker();
            alertMaker.ErrorAlert("Error", s.getMessage());
        }
        try {
            VehicleDAO.create(v);
        } catch (DAOException DAO) {
            throw new ServiceException("vehicle is not created");
        }
        LOG.debug("Vehicle saved ! ", v);

    }

    @Override
    public List<Vehicle> getVehicles() throws ServiceException {
        LOG.debug("User want to showAllReservation a Vehicle information {}  ");
        try {
            return VehicleDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void editVehicle(Vehicle v) throws NotFoundException, ServiceException {
        LOG.debug("User want to updateReservation a Vehicle {}  ", v);
        try {
            VehicleValidation(v);
        } catch (ServiceException s) {
            AlertMaker alertMaker = new AlertMaker();
            alertMaker.ErrorAlert("Error", s.getMessage());
        }
        try {
            VehicleDAO.edit(v);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        LOG.debug("Vehicle edited ! ", v);
    }

    @Override
    public void delete(Vehicle vehicle) throws ServiceException {
        LOG.debug("User want to delet a Vehicle {}  ", vehicle);
        try {
            VehicleDAO.delete(vehicle);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        LOG.debug("Vehicle deleted ! ", vehicle);
    }

    @Override
    public boolean search(int id) throws ServiceException {
        LOG.debug(" search Vehicle {}  ", id);
        try {
            return VehicleDAO.search(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }

    }


    @Override
    public Vehicle getOne(int id) throws ServiceException {
        try {
            return VehicleDAO.getOneVehicle(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    @Override
    public void setInfo(ObservableList<Vehicle> vehicles) {
        this.vehicles = vehicles;

    }

    @Override
    public void close() {
        VehicleDAO.close();
    }
    @Override
    public boolean checkVehicleExistbyDate(Timestamp start, Timestamp end, int vehicleID) throws ServiceException {
        try {
            return VehicleDAO.checkVehicleExistbyDate(start, end, vehicleID);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    @Override
    public int getvID() throws ServiceException {
        try {
            return VehicleDAO.getVehicleID();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Vehicle getVehicleEvenIfIsDeleted(int id) throws ServiceException{
        try {
            return VehicleDAO.getVehicleEvenIfDeleted(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Vehicle> ListOfFreeVehiclesByTime(Timestamp start, Timestamp end, int vehicleID) throws ServiceException {
        try {
            return VehicleDAO.ListOfFreeVehiclesByTime(start, end, vehicleID);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public ObservableList<Vehicle> getInfo() {
        return vehicles;

    }

    @Override
    public List<Vehicle> filterTableVehicle(ArrayList<String> command) throws ServiceException {
        String s = "";
        LOG.debug("Filter Table {}  ");

        if (command.size() >= 1 && isNumeric(command.get(0))) {
            LOG.error("command.get(0)" + command.get(0));
            for (String str : command) {
                if (command.indexOf(str) == 0) {

                    s += " select * from vehicle where id= " + str;
                } else if (command.indexOf(str) >= 1) {

                    s += " or id= " + str;
                }
            }
            LOG.debug(s);

        } else {

            for (String str : command) {
                if (command.indexOf(str) < 2) {

                    s += str;
                } else {
                    s += " and " + str;
                }
            }
            LOG.debug(s);
        }

        try {
            return VehicleDAO.getFilteredTable(s);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * check if the vehicle information is valid.
     *
     * @param vehicle that needs to be validated.
     * @throws ServiceException if the validation failed.
     */
    private void VehicleValidation(Vehicle vehicle) throws ServiceException {


        if (vehicle.getTitle() == null) {
            throw new ServiceException("Title is null ! ");
        }
        if (vehicle.getTitle().equals("")) {
            throw new ServiceException("Title is empty ! ");
        }

        if (vehicle.getYear() == 0) {
            throw new ServiceException("year ist empty !");
        }
        if (!isNumeric(vehicle.getYear() + "")) {
            throw new ServiceException("wrong format for year !");
        }
        if (vehicle.getYear()<1885 || vehicle.getYear() >2018){
            throw new ServiceException("year has to be between 2018 and 1885 !");
        }
        String s = vehicle.getYear() + "";
        if (s.length() != 4) {
            throw new ServiceException("only 4 digit ! ");
        }
        if (!isNumeric(vehicle.getSeats() + "")) {
            throw new ServiceException("only 1-10 !");
        }
        if (!vehicle.getLicenseClass().isEmpty()) {
            if (vehicle.getLicensePlate().isEmpty()) {
                throw new ServiceException("License Plate is empty !");
            }
            if (vehicle.getLicensePlate().equals("")) {
                throw new ServiceException("License Plate is empty !");
            }
        }
        if (!isNumeric(vehicle.getPrice() + "")) {
            throw new ServiceException("wrong format for Price !");
        }
        if (vehicle.getPrice() == 0 || vehicle.getPrice() < 0) {
            throw new ServiceException("wrong format for Price !");
        }

        if (vehicle.getTypeOfDrive().equals("motorized")) {
            if (!isNumeric(vehicle.getPower() + "")) {
                throw new ServiceException("wrong format for power !");
            }
            if (vehicle.getPower() == 0) {
                throw new ServiceException("power is empty!");
            }
        }


    }

    /**
     * check a string to see if it's an integer.
     *
     * @param s is string which is going to be checked if it's an integer or not.
     * @return true if the given string is an integer number.
     */
    private boolean isNumeric(String s) {
        return java.util.regex.Pattern.matches("\\d+", s);
    }


}
