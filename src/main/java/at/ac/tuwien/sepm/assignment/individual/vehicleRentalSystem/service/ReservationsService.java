package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationsService {
    /**
     * Creates a new reservation.
     * sends the specified reservation to the DAO layer and
     * calls the proper method from the DAO .
     *
     * @param reservation is the reservation be created (saved in the DB)
     * @throws ServiceException if the specified reservation is NULL or the reservation validation is not correct.
     */
    void saveReservation(Reservation reservation) throws ServiceException;
    /**
     * update the information in the DB for the  reservation reservation
     * this method calls the proper method from the DAO layer
     *
     * @param reservation object containing the info that needs to be updated
     * @throws NotFoundException if the reservation is not found .
     * @throws ServiceException incase of error
     */
    void editReservation(Reservation reservation) throws NotFoundException, ServiceException;
    /**
     * remove the specified reservation from the database
     * this method calls the proper method from the DAO layer
     *
     * @param reservation reservation that needs to be deleted(isdeleted=false) from the DB
     * @throws NotFoundException if there is no such reservation.
     * @throws ServiceException incase of error
     */
    void cancle(Reservation reservation) throws NotFoundException, ServiceException;
    /**
     * search the reservation with id.
     * this method calls the proper method from the DAO layer
     *
     * @param id is the id of the reservation
     * @return true when the reservation with the given id is found, false otherwise.
     * @throws ServiceException incase of error
     */
    boolean search(int id) throws ServiceException;
    /**
     * finalize the given reservation and set the status from open to closed.
     * this method calls the proper method from the DAO layer
     *
     * @param reservation include all the information of the reservation
     * @throws ServiceException if the finalization is failed.
     * @throws NotFoundException if the reservation to be finalized never found.
     */
    void finalizeReservation(Reservation reservation) throws ServiceException, NotFoundException;
    /**
     * save a new reservation, with a certain bookin id.
     * sends the specified reservation to the DAO layer and
     * calls the proper method from the DAO .
     * this methode is to be used for multiple auto reservation
     * @param reservation is the reservation be created (saved in the DB)
     * @param bookingID is the booking id of the reservation
     * @throws ServiceException if the specified reservation is NULL or the reservation validation is not correct.
     */
    void saveReservationonID(Reservation reservation, int bookingID) throws ServiceException;
    /**
     * returns booking id of the reservation
     * @return  the booking id of a reservation
     * @throws ServiceException if
     */

    int getBookingID() throws ServiceException;
    /**
     * returns if the cancle of a reservation is free of cost or not
     * @param reservation is the reservation to be checked.
     * @return  the cancle of a reservation is free or not
     */
    String checkForCancleFree(Reservation reservation);
    /**
     * search the reservation with the vehicles' id.
     * this method calls the proper method from the DAO layer
     *
     * @param vehicleID is the id of the vehicle
     * @return a list of times , that the vehicle is reserved.
     @throws ServiceException incase of error
     */
    List<String> searchBasedOnVehicleID(int vehicleID) throws ServiceException;
    /**
     * get all the reservations that are saved and not deleted.
     * this method calls the proper method from the DAO layer
     *
     * @return a list of all reservations
     * @throws NotFoundException if error accured.
     * @throws ServiceException incase of error
     */
    List<Reservation> getReservation() throws NotFoundException, ServiceException;
    /**
     * this method is to validate the payment like IBAN or credit card.
     * this method calls the proper method from the DAO layer
     * @param method is either IBAN or credit card.
     * @param number is the code of the IBAN or the credit card.
     * @throws ServiceException incase of error
     */
    void paymentValidation(String method, String number) throws ServiceException;
    /**
     * this method get only one reservation.
     * this method calls the proper method from the DAO layer
     * @param id is a id of the reservation
     * @return   reservation.
     * @throws ServiceException if there is a problem.
     */
    Reservation getOneReservation(int id) throws ServiceException;
    /**
     * this method calculates the price of a vehicle between start and the end.
     * this method calls the proper method from the DAO layer
     * @param start is a start of the reservation
     * @param end is a end of the reservation
     * @param vehicle is a id of the vehicle that is gonna be reserved.
     * @return   the price of the reservation between start and end for the given vehicle .
     */
    double priceCalculation(Timestamp start, Timestamp end, Vehicle vehicle);
    /**
     * sends the close the connection  request to the dao layer .
     */
    void close();
    /**
     * return list of all the reservation (open,closed and canceled.)
     * this method calls the proper method from the DAO layer
     * @return list of all reservations.
     * @throws ServiceException if there is a problem.
     */
    List <Reservation> getInvoices() throws ServiceException;
    /**
     * returns vehicle_id of a reservation based on the bookingid.
     * this method calls the proper method from the DAO layer
     * @return a List of all ids .
     * @param  b is the booking id
     * @throws ServiceException if there is a problem.
     */
    List<Integer> listOFVehicleIDsWithTheSameBookingID(int b) throws ServiceException;
    /**
     * returns list of vehicle_ids in a certain time.
     * this method calls the proper method from the DAO layer
     * @param s start
     * @param e end
     * @return a List of all ids .
     * @throws ServiceException if there is a problem.
     */
    List<Integer> showAllVehcilesReservedInGivenDate(Timestamp s, Timestamp e) throws ServiceException;
    /**
     * add the specified vehicle to a specific reservation.
     * this method calls the proper method from the DAO layer
     *
     * @param vehicle vehicle that needs to be added
     * @param reservation     reservation that needs to be updated.
     * @throws ServiceException if there is a problem during add.
     */
    void add(Reservation reservation,Vehicle vehicle)  throws ServiceException;
    /**
     * cancel a single reservation, with the reservation id.
     * @param x id of the reservation.
     * @throws ServiceException if there is a problem .
     */
    void cancleONid(int x) throws ServiceException;
    /**
     * list of vehicle's id with the same booking_id (open,closed or canceled)
     * @param b id of the invoice.
     * @return list of ids.
     * @throws ServiceException if there is a problem .
     */
    List<Integer> listOFVehicleIDsWithTheSameBookingIDINVOICE(int b) throws ServiceException;
    /**
     * an invoice with the id.
     * @param id id of the invoice.
     * @return the Reservation, which is an invoice.
     * @throws ServiceException if there is a problem .
     */
    Reservation getOneInvoice(int id) throws ServiceException;
    /**
     * list of integer as price of the vehicle with the given booking id, which is the foreign key.
     * @param bookingid end date
     * @return list of prices of the reservation with the same booking id.
     * after reciving the list, all element will be added together and the summe is the totalprice,
     * this way, there will be no direct acces to vehicle , so if the vehicle changes, the reservation stays the same with no change.
     * @throws ServiceException if there is a problem .
     */
    List<Integer> getPrice(int bookingid) throws ServiceException;
    /**
     * list of Reservation in a specific date.
     * @param start start date
     * @param end end date
     * @return list of reservations.
     * @throws ServiceException if there is a problem .
     */
    List<Reservation> ListOfreservationsByTime(Timestamp start, Timestamp end) throws ServiceException;
}
