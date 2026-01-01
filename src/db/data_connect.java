package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class data_connect {

    Connection conn = null;

    public void connectDB() {
        String url = "";
        String user = "";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
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