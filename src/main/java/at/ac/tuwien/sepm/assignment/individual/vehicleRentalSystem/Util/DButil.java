package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBUtil manages the connection to the database h2
 */
public class DButil {

    /**
     * LOG is a Logger instance for this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    /**
     * con is the The DBUtil's connection.
     */
    public static Connection con = null;
    /**
     * Returns a connection to the database (JDBC database)
     * @return connection to database (JDBC database)
     */

    public static Connection getConnection() {
        if (con == null) {
            con = openConnection();
        }

        return con;
    }
    /**
     * opens a connection to the database (JDBC database)
     * @return connection
     */
    public static Connection openConnection() {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
       //   connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
            connection = DriverManager.getConnection("jdbc:h2:~/sepm;INIT=Runscript From 'classpath:sql/createAndInsert.sql'","dbUser","dbPassword");

            //connection = DriverManager.getConnection("jdbc:h2:~/sepm;INIT=Runscript From 'classpath:sql/createAndInsert.sql'","","");
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return connection;
    }

    /**
     * Closes the connection.
     */

    public static void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
