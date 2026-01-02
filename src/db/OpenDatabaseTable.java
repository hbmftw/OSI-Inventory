package db;

/*
 * This will be the basic template on opening database tables and displaying the contents
 */

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class OpenDatabaseTable extends JPanel {

    private ConfigLoader configLoader = new ConfigLoader();
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public OpenDatabaseTable() {
        this.dbUrl = configLoader.getProperty("db.address");
        this.dbUser = configLoader.getProperty("db.username");
        this.dbPassword = configLoader.getProperty("db.password");
    }
    
    public JPanel getDatabaseTablePanel() {

        String url = dbUrl;
        String user = dbUser;
        String password = dbPassword;

        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();

        String sql = "SELECT * FROM OSI-TEST.osi_table;";

        try (Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
                System.out.println("Connected!");

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Get Column Names
                for (int i = 1; i <= columnCount; i++) {
                    columnNames.add(metaData.getColumnName(i));
                }

                // Retrieve Row Data
                while (resultSet.next()) {
                    Vector<Object> row = new Vector<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(resultSet.getObject(i));
                    }
                    data.add(row);
                }
        }
        catch (SQLException e) {e.printStackTrace();}

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        //Add scroll to JPanel
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}
/*
 * This will be the basic template on opening database tables and displaying the contents
 */ 