package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Util.DButil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOJDBC implements ReservationDAO {
    int id;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void createReservation(Reservation reservation) throws DAOException {
        String insert = "INSERT INTO RESERVATION (ID, clientName,payment ,START, END ,vehicle_id,Price,totalPrice,status ) VALUES (default,?,?,?,?,?,?,?,?)";
        LOG.info("save Reservation :{}", reservation);
        if (reservation == null) {
            throw new IllegalArgumentException("NULL Value");
        }
        try {
            PreparedStatement ps = DButil.getConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, reservation.getClientName());
            ps.setString(2, reservation.getPayment());
            ps.setTimestamp(3, reservation.getStart());
            ps.setTimestamp(4, reservation.getEnd());
            ps.setInt(5, reservation.getVehicleID());
            ps.setInt(6, reservation.getPrice());
            ps.setInt(7, reservation.getTotalPrice());
            ps.setString(8, "open");
            ps.executeUpdate();
            ResultSet generatedkeys = ps.getGeneratedKeys();
            generatedkeys.next();
            reservation.setReservation_id(generatedkeys.getInt(1));

        } catch (SQLException e) {
            LOG.error(e.getMessage() + " createReservation failed !");
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void edit(Reservation reservation) throws NotFoundException, DAOException {
        int bookingid = reservation.getReservation_secondID();
        LOG.debug("Edit the Reservation :{}", bookingid);
        int row;
        if (!this.search(bookingid)) {
            LOG.error(" NOT found", bookingid);
            throw new NotFoundException();
        } else {
            try {

                PreparedStatement ps = DButil.getConnection().prepareStatement("UPDATE reservation SET clientName=?,payment=?, START=?,END=?, totalPrice=?,status=? WHERE bookingid = ? ");
                ps.setString(1, reservation.getClientName());
                ps.setString(2, reservation.getPayment());
                ps.setTimestamp(3, reservation.getStart());
                ps.setTimestamp(4, reservation.getEnd());
                ps.setInt(5, reservation.getTotalPrice());
                ps.setString(6, reservation.getStatus());
                ps.setInt(7, +reservation.getReservation_secondID());
                row = ps.executeUpdate();
                LOG.debug("Updated " + row, reservation);
            } catch (SQLException e) {
                LOG.error(e.getMessage() + " edit Reservation failed !");
                throw new DAOException(e.getMessage());
            }
        }

    }

    @Override
    public ArrayList<Reservation> showAllReservation() throws DAOException {
        String selectDatabase = "SELECT * FROM Reservation where status= 'open'  order by start DESC";

        LOG.debug("Entering showAll method");
        ArrayList<Reservation> listOfReservation = new ArrayList<Reservation>();
        ResultSet rs = null;
        try {

            PreparedStatement preparedStatement = DButil.getConnection().prepareStatement(selectDatabase);

            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOG.error(e.getMessage() + " show all the Reservations failed !");

            throw new DAOException(e.getMessage());
        }

        try {
            while (rs.next()) {
                id = rs.getInt(1);
                Reservation r;
                r = new Reservation(rs.getString(2), rs.getString(3), rs.getTimestamp(4),
                    rs.getTimestamp(5), rs.getInt(6), rs.getInt(7), rs.getString(10));

                r.setTotalPrice(rs.getInt(8));

                r.setReservation_secondID(rs.getInt(11));

                r.setReservation_id(id);
                listOfReservation.add(r);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage() + " show all the reservation failed !");
            throw new DAOException(e.getMessage());
        }
        return listOfReservation;
    }

    @Override
    public void cancleReservation(Reservation reservation) {
        LOG.debug(" cancle the reservation: {}", reservation);

        int total = reservation.getTotalPrice();
        try {
            PreparedStatement st = DButil.getConnection().prepareStatement(" UPDATE reservation SET status = 'canceled',totalprice=? WHERE bookingid =? ");
            st.setInt(1, total);
            st.setInt(2, reservation.getReservation_secondID());
            st.executeUpdate();

        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
    }

    @Override
    public boolean search(int id) throws DAOException {
        LOG.debug("Search {}", id);
        PreparedStatement searchStmt;
        ResultSet rs = null;
        try {
            searchStmt = DButil.getConnection().prepareStatement("SELECT * FROM reservation Where id= ?;");
            searchStmt.setInt(1, id);
            rs = searchStmt.executeQuery();
        } catch (SQLException e) {
            LOG.error(e.getMessage() + " search Reservation failed !");

            throw new DAOException(e.getMessage());
        }
        try {
            if (!rs.next()) {
                LOG.error("NOT found!", id);
                return false;
            } else {
                LOG.debug("found!", id);
                return true;
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage() + " search Reservation failed !");
            throw new DAOException("SQLException");
        }
    }


    @Override
    public void createReservationonID(Reservation reservation, int bookingid) throws DAOException {
        String insert = "INSERT INTO RESERVATION (ID, clientName,payment ,START, END ,vehicle_id,Price,totalPrice,status,bookingID ) VALUES (default,?,?,?,?,?,?,?,?,?)";
        LOG.info("save Reservation :{}", reservation);

        try {
            PreparedStatement ps = DButil.getConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, reservation.getClientName());
            ps.setString(2, reservation.getPayment());
            ps.setTimestamp(3, reservation.getStart());
            ps.setTimestamp(4, reservation.getEnd());
            ps.setInt(5, reservation.getVehicleID());
            ps.setInt(6, reservation.getPrice());
            ps.setInt(7, reservation.getTotalPrice());
            ps.setString(8, "open");
            ps.setInt(9, bookingid);
            ps.executeUpdate();
            ResultSet generatedkeys = ps.getGeneratedKeys();
            generatedkeys.next();
            //vehicle.setId(generatedkeys.getLong(1));
            reservation.setReservation_id(generatedkeys.getInt(1));
        } catch (SQLException e) {
            LOG.error(e.getMessage() + " create Reservation based on id failed !");
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public int getBookingID() throws DAOException {
        String select = "select bookingid from reservation where id =(SELECT MAX(ID)  FROM reservation)";
        LOG.info("getBookingID");
        int x = 0;
        ResultSet rs = null;
        try {
            PreparedStatement ps = DButil.getConnection().prepareStatement(select, Statement.RETURN_GENERATED_KEYS);

            rs = ps.executeQuery();
        } catch (SQLException e) {
            LOG.error(e.getMessage() + " get booking id of Reservation failed !");
            throw new DAOException(e.getMessage());
        }
        try {
            while (rs.next()) {
                x = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage() + " get booking id of Reservation failed !");

            throw new DAOException(e.getMessage());
        }
        return x;
    }

    @Override
    public void finalizedReservation(Reservation reservation) {
        LOG.debug(" update the reservation: {}", reservation);

        try {
            PreparedStatement st = DButil.getConnection().prepareStatement("UPDATE reservation SET status = 'closed', FINALIZATIONDATE=? WHERE bookingID =? ");
            st.setTimestamp(1, reservation.getFinalizationDate());
            st.setInt(2, reservation.getReservation_secondID());
            st.executeUpdate();

        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void close() {
        LOG.info("Waiting to close the database!");

        if (DButil.getConnection() != null) {
            LOG.info("Closing Database Connection!");
            DButil.closeConnection();
        }

    }

    @Override
    public List<Reservation> getInvoices() throws DAOException {
        String selectDatabase = "SELECT * FROM Reservation  order by start DESC ";

        LOG.debug("Entering getInvoices method");
        ArrayList<Reservation> listOfInvoices = new ArrayList<Reservation>();
        ResultSet rs = null;
        try {

            PreparedStatement preparedStatement = DButil.getConnection().prepareStatement(selectDatabase);

            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOG.error(e.getMessage() + " get all Invoices failed !");

            throw new DAOException(e.getMessage());
        }

        try {
            while (rs.next()) {
                id = rs.getInt(1);
                Reservation r;
                r = new Reservation(rs.getString(2), rs.getString(3), rs.getTimestamp(4),
                    rs.getTimestamp(5), rs.getInt(6), rs.getInt(7), rs.getString(10));

                r.setTotalPrice(rs.getInt(8));

                r.setReservation_secondID(rs.getInt(11));

                r.setReservation_id(id);
                listOfInvoices.add(r);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage() + " get all Invoices failed !");

            throw new DAOException(e.getMessage());
        }
        return listOfInvoices;
    }

    @Override
    public List<Integer> listOFVehicleIDsWithTheSameBookingID(int b) throws DAOException {

        LOG.debug("Entering showAll method");
        List<Integer> listOfVEHICLE = new ArrayList<Integer>();
        ResultSet rs = null;
        try {

            PreparedStatement preparedStatement = DButil.getConnection().prepareStatement("SELECT vehicle_id FROM Reservation where status= 'open'  and bookingid=? ");
            preparedStatement.setInt(1, b);
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        try {
            while (rs.next()) {

                listOfVEHICLE.add(rs.getInt(1));

            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return listOfVEHICLE;
    }

    @Override
    public List<Integer> listOFVehicleIDsWithTheSameBookingIDINVOICE(int b) throws DAOException {

        LOG.debug("INVOICE VERSION");
        List<Integer> listOfVEHICLE = new ArrayList<Integer>();
        ResultSet rs = null;
        try {

            PreparedStatement preparedStatement = DButil.getConnection().prepareStatement("SELECT vehicle_id FROM Reservation where  bookingid=? ");
            preparedStatement.setInt(1, b);
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }

        try {
            while (rs.next()) {

                listOfVEHICLE.add(rs.getInt(1));

            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());

            throw new DAOException(e.getMessage());
        }

        return listOfVEHICLE;
    }

    @Override
    public List<Integer> showAllVehcilesReservedInGivenDate(Timestamp s, Timestamp e) throws DAOException {
        String selectDatabase = "SELECT distinct( vehicle_id) FROM Reservation where start>=? and end<=? ";
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ResultSet rs = null;


        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DButil.getConnection().prepareStatement(selectDatabase);
            preparedStatement.setTimestamp(1, s);
            preparedStatement.setTimestamp(2, e);
            rs = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            LOG.error(e1.getMessage() + " showAllVehcilesReservedInGivenDate failed !");

            throw new DAOException(e1.getMessage());
        }
        try {
            while (rs.next()) {
                id = rs.getInt(1);
                ids.add(id);
            }
        } catch (SQLException e2) {
            LOG.error(e2.getMessage() + " showAllVehcilesReservedInGivenDate failed !");
            throw new DAOException(e2.getMessage());
        }
        return ids;
    }

    @Override
    public void add(Reservation reservation, Vehicle vehicle) throws DAOException {
        String insert = "INSERT INTO RESERVATION (ID, clientName,payment ,START, END ,vehicle_id,Price,totalPrice,status,bookingid ) VALUES (default,?,?,?,?,?,?,?,?,?)";
        LOG.info("add a new Reservation :{}", reservation);

        try {
            PreparedStatement ps = DButil.getConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, reservation.getClientName());
            ps.setString(2, reservation.getPayment());
            ps.setTimestamp(3, reservation.getStart());
            ps.setTimestamp(4, reservation.getEnd());
            ps.setInt(5, vehicle.getId());
            ps.setInt(6, vehicle.getPrice());
            ps.setInt(7, reservation.getTotalPrice());
            ps.setString(8, "open");
            ps.setInt(9, reservation.getReservation_secondID());
            ps.executeUpdate();
            ResultSet generatedkeys = ps.getGeneratedKeys();
            generatedkeys.next();
            reservation.setReservation_id(generatedkeys.getInt(1));

        } catch (SQLException e) {
            LOG.error(e.getMessage() + " add a Reservation failed !");

            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public int getBookingIDofAReservation(int reservation_id) throws DAOException {
        int i;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DButil.getConnection().prepareStatement("select bookingid from reservation where id=?");
            preparedStatement.setInt(1, reservation_id);
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOG.error(e.getMessage() + " get bookingID of a Reservation failed !");
            throw new DAOException(e.getLocalizedMessage());
        }
        try {
            i = rs.getInt(1);

        } catch (SQLException e) {
            LOG.error(e.getMessage() + " get bookingID of a Reservation failed !");
            throw new DAOException(e.getMessage());
        }

        return i;
    }

    @Override
    public List<String> searchBasedOnVehicleID(int vehicleID) throws DAOException {
        List<String> times = new ArrayList<>();
        LOG.debug("Search {}", vehicleID);

        PreparedStatement searchStmt;
        ResultSet rs = null;
        try {
            searchStmt = DButil.getConnection().prepareStatement("SELECT start,end  FROM reservation Where vehicle_id= ?");
            searchStmt.setInt(1, vehicleID);
            rs = searchStmt.executeQuery();
        } catch (SQLException e) {
            LOG.error(e.getMessage() + " searchBasedOnVehicleID failed !");
            throw new DAOException(e.getMessage());
        }
        try {
            while (rs.next()) {
                String s = "";
                LOG.debug("found!", vehicleID);
                s += rs.getTimestamp(1) + "," + rs.getTimestamp(2);
                times.add(s);

            }
        } catch (SQLException e) {
            LOG.error(e.getMessage() + " searchBasedOnVehicleID failed !");
            throw new DAOException(e.getMessage());
        }
        return times;
    }

    @Override
    public void cancleONid(int x) throws DAOException {
        LOG.debug(" cancle the reservation: {}", x);

        try {
            PreparedStatement st = DButil.getConnection().prepareStatement(" UPDATE reservation SET status = 'canceled' WHERE id =?");
            st.setInt(1, x);
            st.executeUpdate();

        } catch (SQLException e) {
            LOG.error(e.getMessage() + " canceling reservation failed !");
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Integer> getPrice(int bookingid) throws DAOException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ResultSet rs = null;


        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DButil.getConnection().prepareStatement("SELECT price FROM RESERVATION  where bookingid=?");
            preparedStatement.setInt(1, bookingid);
            rs = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            LOG.error(e1.getMessage() + " Vehicle getPrice in reservation failed !");

            throw new DAOException(e1.getMessage());
        }
        try {
            while (rs.next()) {
                id = rs.getInt(1);
                ids.add(id);
            }
        } catch (SQLException e2) {
            LOG.error(e2.getMessage() + " Vehicle getPrice in reservation failed !");
            throw new DAOException(e2.getMessage());
        }
        return ids;
    }

    @Override
    public List<Reservation> ListOfreservationsByTime(Timestamp start, Timestamp end) throws DAOException {
        ArrayList<Reservation> arrayList = new ArrayList<>();
        ResultSet rs = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DButil.getConnection().prepareStatement(" select * from reservation where start BETWEEN ? and ? and end between ? and ? ");

            preparedStatement.setTimestamp(1, start);
            preparedStatement.setTimestamp(2, end);
            preparedStatement.setTimestamp(3, start);
            preparedStatement.setTimestamp(4, end);

            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        try {
            while (rs.next()) {
                id = rs.getInt(1);
                Reservation r;
                r = new Reservation(rs.getString(2), rs.getString(3), rs.getTimestamp(4),
                    rs.getTimestamp(5), rs.getInt(6), rs.getInt(7), rs.getString(10));

                r.setTotalPrice(rs.getInt(8));

                r.setReservation_secondID(rs.getInt(11));

                r.setReservation_id(id);
                arrayList.add(r);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return arrayList;
    }

}

