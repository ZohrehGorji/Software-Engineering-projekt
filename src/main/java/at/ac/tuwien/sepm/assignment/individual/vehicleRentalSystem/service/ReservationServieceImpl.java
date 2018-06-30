package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.ReservationDAO;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao.ReservationDAOJDBC;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.apache.commons.validator.routines.CreditCardValidator;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * The implementation of the ReservationServiece, which impelents ReservationServiece and has implementation of all the methods send requests to DAO level.
 */
public class ReservationServieceImpl implements ReservationsService {
    /**
     * LOG is The Logger of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * reservationDAO is The instance of ReservationDAOJDBC.
     */
    private ReservationDAO reservationDAO = new ReservationDAOJDBC();

    @Override
    public void saveReservation(Reservation reservation) throws ServiceException {
        LOG.info("reservationDAO.createReservation(reservation) {}", reservation);
        if (reservation.getStart().after( reservation.getEnd())){
            throw new ServiceException("Star and End are false.");
        }
        checkAvailable(reservation.getStart(), reservation.getEnd(), reservation.getVehicleID());
        paymentValidation(reservation.getStatus(), reservation.getPayment());

        try {


            reservationDAO.createReservation(reservation);

        } catch (DAOException es) {
            throw new ServiceException("reservation creation failed ! \n cause: " + es.getMessage());
        }
    }

    @Override
    public List<Reservation> getReservation() throws ServiceException {
        LOG.info("User want to showAllReservation a Reservation information !  ");
        try {
            return reservationDAO.showAllReservation();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void editReservation(Reservation reservation) throws NotFoundException, ServiceException {
        LOG.debug("User want to updateReservation a Vehicle {}  ", reservation);
        try {
            reservationDAO.edit(reservation);
        } catch (DAOException e) {
            throw new ServiceException("reservation edit failed ! \n cause: " + e.getMessage());
        }
        LOG.debug("Vehicle edited ! ", reservation);
    }

    @Override
    public void cancle(Reservation reservation) throws ServiceException {
        LOG.info("User want to cancle a reservation !  ", reservation);
        checkForCancleFree(reservation);
        reservationDAO.cancleReservation(reservation);
        LOG.info("reservation canceled ! ", reservation);

    }

    @Override
    public void paymentValidation(String method, String number) throws ServiceException {
        if (method.equals("IBAN")) {
            if (!validateIban(number))
                throw new ServiceException("IBAN number not correct");
        }
        if (method.equals("creditcard")) {
            CreditCardValidator creditCardValidator = new CreditCardValidator();
            if (!creditCardValidator.isValid(number))
                throw new ServiceException("Credit card number not correct");
        }
    }

    @Override
    public boolean search(int id) throws ServiceException {
        LOG.info(" search reservation !  ", id);
        try {
            return reservationDAO.search(id);
        } catch (DAOException e) {
            throw new ServiceException("reservation search failed ! \n cause: " + e.getMessage());
        }
    }

    @Override
    public List<String> searchBasedOnVehicleID(int vehicleID) throws ServiceException {
        try {
            return reservationDAO.searchBasedOnVehicleID(vehicleID);
        } catch (DAOException e) {
            throw new ServiceException("search of reservation based on VehicleID failed ! \n cause: " + e.getMessage());
        }
    }

    @Override
    public void finalizeReservation(Reservation reservation) throws NotFoundException, ServiceException {
        LOG.info("User want to finiliaze a reservation !  ", reservation);
        try {
            reservationDAO.finalizedReservation(reservation);
        } catch (DAOException e) {
            throw new ServiceException("Reservation Finalization failed ! \n cause: " + e.getMessage());
        }
        LOG.info("reservation finilized ! ", reservation);

    }

    @Override
    public void saveReservationonID(Reservation reservation, int bookingID) throws ServiceException {
        LOG.info("reservationDAO.createReservation(reservation)");

        try {
            checkAvailable(reservation.getStart(), reservation.getEnd(), reservation.getVehicleID());
            checkTime(reservation);
        } catch (ServiceException e) {
            LOG.error("Service :\t" + e.getMessage());
            throw new ServiceException("Vehicle is not free to be booked " + e.getMessage());

        }
        try {
            paymentValidation(reservation.getStatus(), reservation.getPayment());
        } catch (ServiceException e) {
            LOG.error("Service :\t" + e.getMessage());
            throw new ServiceException("payment validation is problematic: " + e.getMessage());
        }
        try {

            reservationDAO.createReservationonID(reservation, bookingID);
        } catch (DAOException e) {
            throw new ServiceException("reservation creation error: " + e.getMessage());
        }

    }

    /**
     * validation of the start and the end time
     *
     * @param reservation has start and end date to be checked
     * @throws ServiceException if the checking has a problem
     */
    private void checkTime(Reservation reservation) throws ServiceException {
        LOG.info("Checking if Reservation time is correct!");
        if (reservation.getStart().after(reservation.getEnd())) {
            throw new ServiceException("Start Time should be before End Time! Start:\t" + reservation.getStart() + " End:\t" + reservation.getEnd());

        }
        String s = reservation.getStart().toLocalDateTime().toLocalDate().toString();
        String s2 = LocalDate.now().toString();
        LOG.info(s + s2);
        if (s.equals(s2)) {
            throw new ServiceException("A reservation can't be made on the same day!");
        }
    }


    @Override
    public int getBookingID() throws ServiceException {
        try {
            return reservationDAO.getBookingID();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());

        }
    }

    /**
     * validate IBAN
     *
     * @param iban is the number to be validated
     * @return true if the number is a valid iban number
     */
    private boolean validateIban(String iban) {
        return new IBANCheckDigit().isValid(iban);
    }

    @Override
    public Reservation getOneReservation(int id) throws ServiceException {
        List<Reservation> list = null;
        try {
            list = reservationDAO.showAllReservation();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());

        }
        try {
            if (reservationDAO.search(id)) {
                for (Reservation r : list) {
                    if (r.getReservation_id() == id) {
                        return r;
                    }
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return null;
    }

    @Override
    public Reservation getOneInvoice(int id) throws ServiceException {
        List<Reservation> list = null;
        try {
            list = reservationDAO.getInvoices();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());

        }
        try {
            if (reservationDAO.search(id)) {
                for (Reservation r : list) {
                    if (r.getReservation_id() == id) {
                        return r;
                    }
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Integer> getPrice(int bookingid) throws ServiceException {
        try {
            return reservationDAO.getPrice(bookingid);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> ListOfreservationsByTime(Timestamp start, Timestamp end) throws ServiceException {
        try {
            return reservationDAO.ListOfreservationsByTime(start, end);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    public void checkAvailable(Timestamp startTimeStamp, Timestamp endTimeStamp, int vehicleid) throws ServiceException {
        LOG.debug("Checking the Availability of the Vehicle {}");
        try {
            for (int i = 0; i < reservationDAO.showAllReservation().size(); i++) {

                if (reservationDAO.showAllReservation().get(i).getStart().equals(startTimeStamp) && reservationDAO.showAllReservation().get(i).getVehicleID() == vehicleid) {

                    throw new ServiceException("Selected Vehicle is not available as Vehicle is reserved at the same time!");
                }

                if (startTimeStamp.after(reservationDAO.showAllReservation().get(i).getStart()) &&
                    startTimeStamp.before(reservationDAO.showAllReservation().get(i).getEnd()) &&
                    reservationDAO.showAllReservation().get(i).getVehicleID() == (vehicleid)) {
                    throw new ServiceException("Selected Vehicle is not available!\n cause:\n start time should be after: \n" + reservationDAO.showAllReservation().get(i).getEnd());
                }
                if (startTimeStamp.after(reservationDAO.showAllReservation().get(i).getStart()) &&
                    startTimeStamp.before(reservationDAO.showAllReservation().get(i).getEnd()) &&
                    endTimeStamp.after(reservationDAO.showAllReservation().get(i).getEnd()) &&
                    (reservationDAO.showAllReservation().get(i).getVehicleID() == (vehicleid))) {

                    throw new ServiceException("Selected Vehicle is already reserved, either the start should be after\n" + reservationDAO.showAllReservation().get(i).getEnd() + "or the end should be before \n" + reservationDAO.showAllReservation().get(i).getStart());

                }

                if (startTimeStamp.before(reservationDAO.showAllReservation().get(i).getStart()) &&
                    startTimeStamp.before(reservationDAO.showAllReservation().get(i).getEnd()) &&
                    endTimeStamp.before(reservationDAO.showAllReservation().get(i).getEnd()) &&
                    reservationDAO.showAllReservation().get(i).getVehicleID() == (vehicleid) &&
                    endTimeStamp.after(reservationDAO.showAllReservation().get(i).getStart())) {
                    throw new ServiceException("Selected Vehicle is already reserved, either the end should be before\n" + reservationDAO.showAllReservation().get(i).getStart() + "or the start and end should be after \n" + reservationDAO.showAllReservation().get(i).getEnd());
                }

                if (startTimeStamp.before(reservationDAO.showAllReservation().get(i).getStart()) &&
                    startTimeStamp.before(reservationDAO.showAllReservation().get(i).getEnd()) &&
                    endTimeStamp.before(reservationDAO.showAllReservation().get(i).getStart()) &&
                    endTimeStamp.after(reservationDAO.showAllReservation().get(i).getEnd()) &&
                    reservationDAO.showAllReservation().get(i).getVehicleID() == (vehicleid)) {
                    throw new ServiceException("Selected Vehicle is not available \n The start is ok but the end schould be before \n" + reservationDAO.showAllReservation().get(i).getStart());

                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public String checkForCancleFree(Reservation reservation) {
        if (reservation.getStart().getTime() - System.currentTimeMillis() >= 7 * 24 * 60 * 60 * 1000) {
            LOG.info("Reservation Cancellation is free of Cost!");
            return "Reservation Cancellation is free of Cost!";
        } else if (reservation.getStart().getTime() - System.currentTimeMillis() >= 72 * 60 * 60 * 1000) {
            int total = reservation.getTotalPrice();
            total += ((reservation.getTotalPrice() * 40) / 100);
            reservation.setTotalPrice(total);
            LOG.info("Reservation Cancellation is not free of Cost! and Cancellation Cost is: " + total);
            return "Reservation Cancellation is not free of Cost! and Cancellation Cost is: " + total;
        } else if (reservation.getStart().getTime() - System.currentTimeMillis() >= 24 * 60 * 60 * 1000) {
            int total = reservation.getTotalPrice();
            total += ((reservation.getTotalPrice() * 70) / 100);
            reservation.setTotalPrice(total);

            LOG.info("Reservation Cancellation is not free of Cost! and Cancellation Cost is: " + total);
            return "Reservation Cancellation is not free of Cost! and Cancellation Cost is: " + total;
        } else if (reservation.getStart().getTime() - System.currentTimeMillis() < 24 * 60 * 60 * 1000) {
            LOG.error("Reservation Cancellation is not possible!");
            return "Reservation Cancellation is not possible!";
        }
        return null;
    }


    @Override
    public double priceCalculation(Timestamp start, Timestamp end, Vehicle vehicle) {
        double totalPrice = 0;
        long seconds = (end.getTime() - start.getTime()) / 1000;
        int minutes = (int) (seconds / 60);
//        int hours = (int) (seconds / 3600);

        totalPrice = (vehicle.getPrice() * minutes) / 60;
        if (totalPrice > 0)
            return totalPrice;
        else return 0.0;

    }

    @Override
    public void close() {
        reservationDAO.close();
    }

    @Override
    public List<Reservation> getInvoices() throws ServiceException {
        try {
            return reservationDAO.getInvoices();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());

        }
    }

    @Override
    public List<Integer> listOFVehicleIDsWithTheSameBookingID(int b) throws ServiceException {
        try {
            return reservationDAO.listOFVehicleIDsWithTheSameBookingID(b);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());

        }
    }

    @Override
    public List<Integer> showAllVehcilesReservedInGivenDate(Timestamp s, Timestamp e) throws ServiceException {
        try {
            return reservationDAO.showAllVehcilesReservedInGivenDate(s, e);
        } catch (DAOException e1) {
            throw new ServiceException(e1.getMessage());
        }
    }

    @Override
    public void add(Reservation reservation, Vehicle vehicle) throws ServiceException {
        try {
            reservationDAO.add(reservation, vehicle);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void cancleONid(int x) throws ServiceException {
        try {
            reservationDAO.cancleONid(x);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());

        }
    }

    @Override
    public List<Integer> listOFVehicleIDsWithTheSameBookingIDINVOICE(int b) throws ServiceException {

        try {
            return reservationDAO.listOFVehicleIDsWithTheSameBookingIDINVOICE(b);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }

    }


}
