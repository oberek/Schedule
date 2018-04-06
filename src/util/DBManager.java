package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    public static final String DB_NAME = "U04oOs";
    public static final String DRIVER_URL = "com.mysql.jdbc.Driver";
    private static final String ALLOW_MULTI_QUERIES = "?allowMultiQueries=true";
    public static final String CONNECTION_STRING = "jdbc:mysql://52.206.157.109/" + DB_NAME + ALLOW_MULTI_QUERIES;
    public static final String USER_NAME = "U04oOs";
    public static final String DB_PASSWORD = "53688301999";
    private static Connection conn;
    private Statement statement;

    private DBManager() {

    }
    
    public static Connection getConnection() {
        return conn;
    }

    public static boolean open() {
//    Connection String
//    Server name: 52.206.157.109
//    Database name: U04oOs
//    Username: U04oOs
//    Password: 53688301999
        try {
            Class.forName(DRIVER_URL);
            conn = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, DB_PASSWORD);
            System.out.println("Connected to database: " + DB_NAME);
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Well this is awkward. Couldn't close connection... " + e.getMessage());
            e.printStackTrace();
        }
    }
}
