package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.dao;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Util.DButil;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationServieceImpl;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * The implementation of the VehicleDAOTests, which impelents VehicleDAOTests and has implementation of all the methods and connect with  H2 database.
 */
public class VehicleDAOJDBC implements VehicleDAO {
    /**
     * id is the vehicle's id.
     */
    int id;
    /**
     * LOG is The Logger of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * save the Vehicle into the H2 database
     *
     * @param vehicle is to be stored in H2
     * @throws DAOException if the saving failed.
     */
    @Override
    public void create(Vehicle vehicle) throws DAOException {
        String insert = "INSERT INTO VEHICLE (ID,title,year, description,seats,licensePlate, typeOfDrive, power,price,imgPath,licenseClass,isdeleted ) VALUES (default,?,?,?,?,?,?,?,?,?,?,?)";
        LOG.debug("Create vehicle :{}", vehicle);

        if (vehicle == null) {
            LOG.error("NULL Value");
            throw new IllegalArgumentException("NULL Value");

        }
        try {
            PreparedStatement ps = DButil.getConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, vehicle.getTitle());
            ps.setInt(2, vehicle.getYear());
            ps.setString(3, vehicle.getDescription());
            ps.setInt(4, vehicle.getSeats());
            ps.setString(5, vehicle.getLicensePlate());
            ps.setString(6, vehicle.getTypeOfDrive());
            ps.setInt(7, vehicle.getPower());
            ps.setInt(8, vehicle.getPrice());
            ps.setString(9, vehicle.getImgPath());
            ps.setString(10, vehicle.getLicenseClass());
            ps.setBoolean(11, vehicle.isDeleted());
            ps.executeUpdate();
            ResultSet generatedkeys = ps.getGeneratedKeys();
            generatedkeys.next();

            vehicle.setId(generatedkeys.getInt(1));

        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DAOException("Could not create the vehicle \n Cause: " + e.getMessage());
        }
    }

    /**
     * search a Vehicle from the H2 database
     *
     * @param id is the id if the vehicle that should be searched.
     * @return true if the vehicle with the given id exist in H2. false otherwise.
     * @throws DAOException if a problem accrued during the searching.
     */
    @Override
    public boolean search(int id) throws DAOException {
        LOG.debug("Search the Vehicle {}", id);

        PreparedStatement searchStmt;
        ResultSet rs = null;
        try {
            searchStmt = DButil.getConnection().prepareStatement("SELECT * FROM Vehicle Where id= ? and isdeleted is false;");
            searchStmt.setInt(1, id);
            rs = searchStmt.executeQuery();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DAOException("creating the vehicle was not possible ! \n Cause: " + e.getMessage());
        }
        try {
            if (!rs.next()) {
                LOG.debug("Vehicle with the ID NOT found!: ", id);
                return false;
            } else {
                LOG.debug("Vehicle with the ID found: ", id);
                return true;
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DAOException("executing search statement failed !\nCause: " + e.getMessage());

        }
    }

    /**
     * edit a Vehicle in the H2 database
     *
     * @param vehicle is the vehicle that should be edited.
     * @throws DAOException      if a problem accrued during the editing.
     * @throws NotFoundException if there is no such vehicle existing in the vehicle.
     */
    @Override
    public void edit(Vehicle vehicle) throws DAOException, NotFoundException {
        int id = vehicle.getId();
        LOG.debug("Edit the Vehicle :{}", id);

        int row;
        if (!this.search(id)) {
            LOG.error(" NOT found", id);
            throw new NotFoundException();
        } else {
            try {
                PreparedStatement ps = DButil.getConnection().prepareStatement("UPDATE Vehicle SET title=?,year=?, description=?,seats=?,licensePlate=?," +
                    " typeOfDrive=?, power=?,price=?,imgPath=?,licenseClass =?,lasteditdate =? ,isdeleted=? WHERE ID = ?");
                ps.setString(1, vehicle.getTitle());
                ps.setInt(2, vehicle.getYear());
                ps.setString(3, vehicle.getDescription());
                ps.setInt(4, vehicle.getSeats());
                ps.setString(5, vehicle.getLicensePlate());
                ps.setString(6, vehicle.getTypeOfDrive());
                ps.setInt(7, vehicle.getPower());
                ps.setInt(8, vehicle.getPrice());
                ps.setString(9, vehicle.getImgPath());
                ps.setString(10, vehicle.getLicenseClass());
                ps.setTimestamp(11, vehicle.getLastEdit());
                ps.setBoolean(12, false);//false because not deleted.
                ps.setInt(13, vehicle.getId());
                row = ps.executeUpdate();
                LOG.debug("Updated " + row, vehicle);
            } catch (SQLException e) {
                LOG.error(e.getMessage());
                throw new DAOException(e.getMessage());
            }
        }

    }

    /**
     * cancle a Vehicle from the H2 database
     *
     * @param vehicle is the vehicle that should be deleted.
     */
    @Override
    public void delete(Vehicle vehicle) throws DAOException {
        LOG.debug(" Delete the VEHICLE: {}", vehicle);

        try {
            PreparedStatement st = DButil.getConnection().prepareStatement("UPDATE vehicle SET isDeleted = true WHERE ID =? ");
            st.setInt(1, vehicle.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DAOException("executing delet statement failed !\nCause: " + e.getMessage());
        }
    }

    /**
     * get a Vehicle from the H2 database
     *
     * @param id is the id of the vehicle that should be searched.
     * @return a vehicle with the given id.
     * @throws DAOException if a problem accured during the search.
     */
    @Override
    public Vehicle getOneVehicle(int id) throws DAOException {
        LOG.debug("get the Vehicle :{}", id);

        Vehicle vehicle = null;
        try {
            PreparedStatement searchStmt = DButil.getConnection().prepareStatement("SELECT * FROM Vehicle Where id= ? and isdeleted is false;");
            searchStmt.setInt(1, id);
            ResultSet rs = searchStmt.executeQuery();
            rs.next();
            vehicle = new Vehicle(rs.getString(2), rs.getInt(3), rs.getString(4),
                rs.getInt(5), rs.getString(6), rs.getString((7)),
                rs.getInt(8), rs.getInt(9), rs.getString(10), rs.getString(11));

            LOG.info(" Vehicle {}", vehicle);
        } catch (SQLException e) {
            LOG.error("ERROR   {}", e.getLocalizedMessage());
            throw new DAOException(e.getMessage());
        }

        return vehicle;

    }

    /**
     * get a list of Vehicles from the H2 database
     *
     * @param string is the sql command which contains all the filter criteria.
     * @return a list of vehicle with the matching criteria.
     */
    @Override
    public List<Vehicle> getFilteredTable(String string) throws DAOException {
        ArrayList<Vehicle> listOfVehicles = new ArrayList<Vehicle>();
        ResultSet rs = null;
        try {

            PreparedStatement preparedStatement = DButil.getConnection().prepareStatement(string);

            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        try {
            while (rs.next()) {
                id = rs.getInt(1);
                Vehicle v;
                v = new Vehicle(rs.getString(2), rs.getInt(3), rs.getString(4),
                    rs.getInt(5), rs.getString(6), rs.getString((7)),
                    rs.getInt(8), rs.getInt(9), rs.getString(10), rs.getString(11));
                v.setId(id);
                listOfVehicles.add(v);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        LOG.info(listOfVehicles.size() + "");
        return listOfVehicles;
    }

    @Override
    public void close() {
        LOG.info("Waiting to close the database!");

        if (DButil.getConnection() != null) {
            LOG.info("Closing Database Connection!");
            DButil.closeConnection();
        }


    }

    /**
     * get list of all exsiting and not deleted Vehicles from the H2 database
     *
     * @return a list of vehicle that are not deleted.
     */
    @Override
    public List<Vehicle> getAll() throws DAOException {
        String selectDatabase = "SELECT * FROM VEHICLE WHERE isdeleted= FALSE";

        LOG.debug("getAll Vehicles:");
        ArrayList<Vehicle> listOfVehicles = new ArrayList<Vehicle>();
        ResultSet rs = null;
        try {

            PreparedStatement preparedStatement = DButil.getConnection().prepareStatement(selectDatabase);

            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }

        try {
            while (rs.next()) {
                id = rs.getInt(1);
                Vehicle v;
                v = new Vehicle(rs.getString(2), rs.getInt(3), rs.getString(4),
                    rs.getInt(5), rs.getString(6), rs.getString((7)),
                    rs.getInt(8), rs.getInt(9), rs.getString(10), rs.getString(11));
                v.setId(id);
                listOfVehicles.add(v);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return listOfVehicles;
    }

    @Override
    public List<Vehicle> ListOfFreeVehiclesByTime(Timestamp start, Timestamp end, int vehicleID) throws DAOException {
        ArrayList<Vehicle> arrayList = new ArrayList<>();
        ResultSet rs = null;


        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DButil.getConnection().prepareStatement(" select * from vehicle where id not in (select distinct(vehicle_id) from reservation where start BETWEEN ? and ? and end between ? and ? )and isdeleted=false");

            preparedStatement.setTimestamp(1, start);


            preparedStatement.setTimestamp(2, end);
            preparedStatement.setTimestamp(3, start);
            preparedStatement.setTimestamp(4, end);
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());        }
        try {
            while (rs.next()) {
                id = rs.getInt(1);
                Vehicle v;
                v = new Vehicle(rs.getString(2), rs.getInt(3), rs.getString(4),
                    rs.getInt(5), rs.getString(6), rs.getString((7)),
                    rs.getInt(8), rs.getInt(9), rs.getString(10), rs.getString(11));
                v.setId(id);
                arrayList.add(v);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return arrayList;
    }

    //select * from vehicle where id not in (select distinct(vehicle_id) from reservation where start BETWEEN '2018-04-11 08:30:00' and '2018-04-13 11:30:00' and end between '2018-04-11 08:30:00' and '2018-04-13 11:30:00' )
    @Override
    public boolean checkVehicleExistbyDate(Timestamp start, Timestamp end, int vehicleID) throws DAOException {
        LOG.debug("check if vehicle exist by given date !:");
        Boolean b = false;
        ResultSet resultSet = null;
        try {

            PreparedStatement preparedStatement = DButil.getConnection().prepareStatement(" select * from vehicle where EXISTS(select start,end from reservation where vehicle_id=? and start >=? and end<= ?) and id=?");
            preparedStatement.setInt(1, vehicleID);

            preparedStatement.setTimestamp(2, start);
            preparedStatement.setTimestamp(3, end);
            preparedStatement.setInt(4, vehicleID);


            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                b = true;
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return b;

    }

    @Override
    public int getVehicleID() throws DAOException {
        String select = "select id from vehicle where id =(SELECT MAX(ID)  FROM vehicle)";
        LOG.info("get vehicle ID");
        int x = 0;
        ResultSet rs = null;
        try {
            PreparedStatement ps = DButil.getConnection().prepareStatement(select, Statement.RETURN_GENERATED_KEYS);

            rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        try {
            while (rs.next()) {
                x = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return x;
    }

    @Override
    public Vehicle getVehicleEvenIfDeleted(int id) throws DAOException {
        LOG.debug("get the Vehicle :{}", id);

        Vehicle vehicle = null;
        try {
            PreparedStatement searchStmt = DButil.getConnection().prepareStatement("SELECT * FROM Vehicle Where id= ? ");
            searchStmt.setInt(1, id);
            ResultSet rs = searchStmt.executeQuery();
            rs.next();
            vehicle = new Vehicle(rs.getString(2), rs.getInt(3), rs.getString(4),
                rs.getInt(5), rs.getString(6), rs.getString((7)),
                rs.getInt(8), rs.getInt(9), rs.getString(10), rs.getString(11));

            LOG.info(" Vehicle {}", vehicle);
        } catch (SQLException e) {
            LOG.error("ERROR   {}", e.getLocalizedMessage());
            throw new DAOException(e.getMessage());
        }

        return vehicle;

    }


}


