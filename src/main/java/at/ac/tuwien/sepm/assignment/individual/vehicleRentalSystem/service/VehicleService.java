package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import javafx.collections.ObservableList;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
/**
 * An interface for Vehicles service .
 */

public interface VehicleService {

    /**
     * Creates a new Vehicle.
     * sends the specified vehicle to the DAO layer and
     * calls the proper method from the DAO .
     *
     * @param v the Vehicle be created (saved in the DB)
     * @throws ServiceException if the specified Vehicle is NULL or the vehicle validation is not correct.
     */
    void createVehicle(Vehicle v) throws ServiceException;

    /**
     * remove the specified vehicle from the database
     * this method calls the proper method from the DAO layer
     *
     * @param vehicle vehicle that needs to be deleted(isdeleted=false) from the DB
     * @throws NotFoundException if there is no such vehicle.
     * @throws ServiceException incase of error.
     */
    void delete(Vehicle vehicle) throws NotFoundException,ServiceException;

    /**
     * search the vehicle with id.
     * this method calls the proper method from the DAO layer
     *
     * @param id is the id to be searched.
     * @return true when the vehicle with the given id is found, false otherwise.
     * @throws ServiceException if the search is failed.
     */
    boolean search(int id) throws ServiceException;

    /**
     * update the information in the DB for the  vehicle v
     * this method calls the proper method from the DAO layer
     *
     * @param v object containing the info that needs to be updated
     * @throws NotFoundException if the vehicle is not found .
     * @throws ServiceException if the vehicle validation is failed.
     */
    void editVehicle(Vehicle v) throws NotFoundException, ServiceException;

    /**
     * this method calls the proper method from the DAO layer
     *
     * @return a List containing all Vehicles.
     * @throws NotFoundException if not vehicle is found.
     * @throws ServiceException incase DAO can't deliver the list.
     */
    List<Vehicle> getVehicles() throws NotFoundException, ServiceException;
    /**
     * this method calls the proper method from the DAO layer
     * @param id of the vehicle
     * @return a vehicle with the given id.
     * @throws ServiceException if the vehicle is not found.
     */
    Vehicle getOne(int id) throws ServiceException;

    /**
     * Returns a list of vehicle results filtered by the command which icludes all parameter given bei user.
     *
     * @param command is an arraylist of string and containing the different paramerters for searching.
     * @return the filtered list of vehicle results if no errors happens.
     * @throws ServiceException if DAO layer can't return the list.
     */
    List<Vehicle> filterTableVehicle(ArrayList<String> command) throws ServiceException;
    /**
     * A getter for vehicle.
     * @return vehicle list .
     */
    ObservableList<Vehicle> getInfo();
    /**
     * A setter for the vehicle list,which will be used in reservation choose vehicle.
     * @param vehicles the new vehicle list.
     */
    void setInfo(ObservableList<Vehicle> vehicles);
    /**
     * send the close the connection request to dao layer .
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
     * @throws ServiceException incase of error
     */
    boolean checkVehicleExistbyDate(Timestamp start, Timestamp end, int vehicleID) throws ServiceException;
    /**
     * Returns a the id of the last vehicle in table of vehicles.
     * will be used for image saving.
     * @return the id of the last vehicle in table.
     * @throws ServiceException if DAO layer can't get the id.
     */
    int getvID() throws ServiceException;
    /**
     * Returns a the id of the last vehicle in table of vehicles.
     * will be used for image saving.
     * @param id is the id of a vehicle
     * @return vehicle even if it is flagged as deleted.
     * in some cases we need the information of the vehicle but the other method
     * returns only vehicle that arent flagged as deleted, this methode returns
     * the vehicle even if it is flag= deleted = true.
     * @throws ServiceException if DAO layer can't get the vehicle.
     */
    Vehicle getVehicleEvenIfIsDeleted(int id) throws ServiceException;
    /**
     * Returns a List of vehicles that are free between two given dates.
     * @param vehicleID is the id of vehicle
     * @param start start date
     * @param end end date
     * @return a List of free vehicles between start and end date.
     * @throws ServiceException if DAO layer can't get the vehicles.
     */
    List<Vehicle> ListOfFreeVehiclesByTime(Timestamp start, Timestamp end, int vehicleID) throws ServiceException;

}
