package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
/**
 * An interface for saving Vehicles into the persistence.
 */
public interface VehicleDAO {
    /**
     * saves the Vehicle v in the persistence
     *
     * @param v the Vehicle that needs to be saved in the DB
     * @throws DAOException if something is wrong during saving the vehicle.
     */
    void create(Vehicle v) throws DAOException;
    /**
     * delet the Vehicle from the database (by changing isDeleted flag from false to true)
     *
     * @param vehicle the Vehicle that needs to be deleted in the DB
     * @throws DAOException if something is wrong during deleting the vehicle.
     *
     */
    void delete(Vehicle vehicle) throws DAOException;
    /**
     * search in the database for a specific vehicle
     *
     * @param id the unique id of the vehicle to be searched after
     * @return  true when found
     *          and when not found throws DAOException
     * @throws DAOException if there is a problem during search.
     */
    boolean search(int id) throws DAOException;
    /**
     * edit the information in the DB of a certain Vehicle
     * @param v object containing the info that needs to be edited
     * @throws NotFoundException if the vehicle to be edited is not found.
     * @throws DAOException if there is a problem during editing.
     */
    void edit(Vehicle v) throws NotFoundException, DAOException;
    /**
     * get all the Vehicles
     * @return a List of non-deleted Vehicle
     * @throws DAOException incase of error
     */
    List<Vehicle> getAll() throws DAOException;
    /**
     * get a Vehicle with the certain id
     * @param id based on this id, vehicle will be searched to find.
     * @return a vehicle with the given id.
     * @throws DAOException if there is a problem during getting the vehicle.
     */
    Vehicle getOneVehicle(int id) throws DAOException;
    /**
     * get all the Vehicles
     * @param s is the command line which is an sql line.
     * @return a List of filtered Vehicle
     * @throws DAOException incase of error
     */
    List<Vehicle> getFilteredTable(String s) throws DAOException;
    /**
     * close the connection to the database after closing the program.
     */
    void close();
    /**
     * use in table filtering based on date of start and end of a vehicle reservation.
     * if the end is not given and == null, then end will be 2050 .
     * if the start is not given == null , then the start will be 2010.
     * @param start is the start date of a reservation.
     * @param end is the start end of a reservation.
     * @param vehicleID is the id of the vehicle that is to be searched.
     * @return true if the vehicle is reserved between start and the end.
     * @throws DAOException incase of error
     *
     */
    boolean checkVehicleExistbyDate(Timestamp start, Timestamp end, int vehicleID) throws DAOException;
    /**
     * get id of the vehicle which is the last on the table .
     *this method will be used for saving the images in imgfolder,
     * with help of this methode each image will be saved with id of the save vehicle.
     * @return a vehicle_id .
     * @throws DAOException if there is a problem during getting the vehicle_id.
     */
     int getVehicleID() throws DAOException;
    /**
     * get the vehicle with the given id.
     * this method will be used for getting information of a vehicle but sometimes
     * the vehicle is flaged as deleted and the method "Vehicle getOneVehicle" can't be
     * used because it only returns the non-deleted vehicle.
     * @param id is the id of the vehicle
     * @return a vehicle
     * @throws DAOException if there is a problem during getting the vehicle
     */
     Vehicle getVehicleEvenIfDeleted(int id) throws DAOException;
    /**
     * get the vehicles with the given id
     * @param start start
     * @param end end
     * @param vehicleID id
     * @return a list of vehicle free in time
     * @throws DAOException if there is a problem during getting the vehicle
     *
     */
    List<Vehicle> ListOfFreeVehiclesByTime(Timestamp start, Timestamp end, int vehicleID) throws DAOException;
}
