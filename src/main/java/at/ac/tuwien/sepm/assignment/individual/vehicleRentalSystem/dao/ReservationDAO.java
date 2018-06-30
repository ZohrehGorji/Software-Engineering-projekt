package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
/**
 * An interface for saving reservations into the persistence.
 */
public interface ReservationDAO {
    /**
     * saves the reservation in the persistence
     *
     * @param reservation is the reservation that needs to be saved in the DB
     * @throws DAOException if something is wrong during saving the reservation.
     */
    void createReservation(Reservation reservation) throws DAOException;
    /**
     * get all the reservations
     * @return a List of non-deleted reservations
     * @throws DAOException if something is wrong during show all the reservation.
     */
    List<Reservation> showAllReservation() throws DAOException;

    /**
     * cancle the reservation from the database (by changing isDeleted flag from false to true)
     * and the status will be changed from open to cancelled.
     *
     * @param reservation is the reservation that needs to be deleted in the DB
     *
     */
    void cancleReservation(Reservation reservation);
    /**
     * search in the database for a specific reservation
     *
     * @param id the unique id of the reservation to be searched after
     * @return  true when found and when not found throws DAOException
     * @throws DAOException if there is a problem during search.
     */
    boolean search(int id) throws DAOException;
    /**
     * edit the information in the DB of a certain reservation
     *
     * @param reservation object containing the info that needs to be edited
     * @throws NotFoundException if the vehicle to be edited is not found.
     * @throws DAOException if there is a problem during editing.
     */
    void edit(Reservation reservation) throws NotFoundException, DAOException;
    /**
     * search in the database for a specific reservation based on the given id of a vehicle
     *
     * @param id the unique id of the vehicle to be searched after
     * @return  a list of dates and times, which is the time of booking of a reserved vehicle.
     * @throws DAOException if there is a problem during search.
     */
    List<String> searchBasedOnVehicleID(int id) throws DAOException;
    /**
     * create a reservation with an id, this id is the next available id in database.
     * this methode will be used incase of multiple auto reservation in one Invoice.
     * @param reservation is the reservation information to be saved.
     * @param bookingid is the id of a booking.
     *
     * @throws DAOException if there is a problem during search.
     */
    void createReservationonID(Reservation reservation, int bookingid) throws DAOException;
    /**
     * gets the booking id of a reservation, which can stay the same for multiple cars, that means those cars are all in one reservation.
     * @return a booking id, which is the second if of a reservation.
     * @throws DAOException if something is wrong during getting last booking id of the reservation.
     */
    int getBookingID() throws DAOException;
    /**
     * finalization of a reservation, which changes the status from open to closed.
     *
     * @param reservation object containing the info of the reservation.
     * @throws NotFoundException if the reservation to be finalized is not found.
     * @throws DAOException if there is a problem during finalization.
     */

    void finalizedReservation(Reservation reservation) throws DAOException, NotFoundException;
    /**
     * close the connection to the database after closing the program.
     */
    void close();
    /**
     * get all the invoices
     * @return a List of all reservations (open,closed and canceled)
     *  @throws DAOException if something is wrong during get invoices the reservation.
     */
    List <Reservation> getInvoices() throws DAOException;
    /**
     * returns list of vehicle_ids of a reservation based on the bookingid.
     * @return a List of all ids .
     * @param b is the bookingid
     * @throws DAOException in case of problem
     */
    List<Integer> listOFVehicleIDsWithTheSameBookingID(int b) throws DAOException;
    /**
     * returns a list of vehicle_id (Distinct) for a given date from start to end.
     * @return list of integers
     * @param s start date
     * @param  e end date
     * @throws DAOException if there is a problem during methode.
     */
    List<Integer> showAllVehcilesReservedInGivenDate(Timestamp s, Timestamp e) throws DAOException;
    /**
     * add a new vehicle to a exsiting reservation. (only when flag is open)
     * @param reservation a reservation that the vehicle need to be added into.
     * @param vehicle is the vehicle to be added.
     * @throws DAOException if there is a problem during adding the vehicle
     */
    void add(Reservation reservation, Vehicle vehicle) throws DAOException;
    /**
     * get the booking id of a vehicle (second key)
     * @param reservation_id is the reservation id.
     * @return the booking id of the reservation
     * @throws DAOException if there is a problem
     */
    int getBookingIDofAReservation(int reservation_id) throws DAOException;
    /**
     * cancle the reservation with given id .
     * @param x id
     *
     * @throws DAOException if there is a problem
     */
    void cancleONid(int x) throws DAOException;
    /**
     * add a new vehicle to a exsiting reservation. (only when flag is open)
     * @param b id.
     * @return the list of integer of vehicle id .
     * @throws DAOException if there is a problem
     */
    List<Integer> listOFVehicleIDsWithTheSameBookingIDINVOICE(int b) throws DAOException;
    /**
     * list of integer as price of the vehicle with the given booking id, which is the foreign key.
     * @param bookingid end date
     * @return list of prices of the reservation with the same booking id.
     * after reciving the list, all element will be added together and the summe is the totalprice,
     * this way, there will be no direct access to vehicle , so if the vehicle changes, the reservation stays the same with no change.
     * @throws DAOException if there is a problem .
     */
    List<Integer> getPrice(int bookingid) throws DAOException;
    /**
     * list of Reservation in a specific date.
     * @param start start date
     * @param end end date
     * @return list of reservations.
     * @throws DAOException if there is a problem .
     */
    List<Reservation> ListOfreservationsByTime(Timestamp start, Timestamp end) throws DAOException;
}
