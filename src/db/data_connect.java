package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class data_connect {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    private ConfigLoader configLoader = new ConfigLoader();

    public data_connect() {
        this.dbUrl = configLoader.getProperty("db.address");
        this.dbUser = configLoader.getProperty("db.username");
        this.dbPassword = configLoader.getProperty("db.password");
    }
    Connection conn = null;

    public void connectDB() {

        String user = dbUser;
        String password = dbPassword;

        try (Connection conn = DriverManager.getConnection(dbUrl, user, password)) {
            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    public void closeDB() throws SQLException {
        conn.close();
    }

    

}