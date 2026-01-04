package db;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//TODO: This is not running !!!!!!!!!!!!!!!!!!!!!!!!!!

/*
    First addition will use date time here, but it is written in action listener in OSIMenu
    This will need to be re-written in OSIMenu to pass just the db, user, and pass using a dialog box
 */

public class DatabaseBackup extends JPanel{

    // Database credentials
    private String user = "postgres";
    private String password = "postgres";
    private static ConfigLoader config = new ConfigLoader();
    private static final String DEFAULT_URL = config.getProperty("db.name");

    //Constructor
    public DatabaseBackup() {
        super();
    }

    // Getters and Setters
    private void setUsername(String username) {this.user = username;}
    private void setPassword(String pwd) {this.password = pwd;}
    //private String getUsername() {return this.user;}
    //private String getPassword() {return this.password;}
    
    public void backupDatabase(String dbName, String User, String Pass){
        
        // Create timestamped filename
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String backupFilePath = "backups/osi_backup_" + timestamp + ".sql";

        // Prepare the command
        // Command: mysqldump -u [user] -p [pass] [database] -r [file]
        ProcessBuilder pb = new ProcessBuilder(
            "mysqldump",
            "-u" + User,
            "-p" + Pass,
            dbName,
            "-r", backupFilePath
        );
        // Try and connect to db and run mysqldump
        try {
            // Start the process
            Process process = pb.start();
            // wait for it to finish running and check the results
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                JOptionPane.showMessageDialog(getComponentPopupMenu(), "Backup successful!\n" + backupFilePath);
            }
            if (exitCode == 1){
                JOptionPane.showMessageDialog(getComponentPopupMenu(), "Backup Failed!");;
            }
            
        }
        catch (IOException | InterruptedException e) { 
            JOptionPane.showMessageDialog(getComponentPopupMenu(), "Backup Failed!\nDuring backup.");
            e.printStackTrace();
        }
    }

    public void backConn() {
        // Set Layout
        this.setLayout(new GridLayout(3, 2, 5, 5));
        this.setBackground(Color.LIGHT_GRAY);
        
        // Create and add components (username field)
        JLabel userName = new JLabel("Username:");
        this.add(userName);
        JTextField uTextField = new JTextField(20);
        this.add(uTextField);

        // Create and add components (password field)
        JLabel pwdLabel = new JLabel("Password:");
        this.add(pwdLabel);
        JPasswordField pwdField = new JPasswordField(20);
        this.add(pwdField);

        //TODO: Need to add a cancel button.
        //Login Button
        javax.swing.JButton loginBtn = new javax.swing.JButton("Backup");

        
        // Set visibility
        this.setVisible(getFocusTraversalKeysEnabled());

        // Action Listener for login button
        loginBtn.addActionListener((ActionEvent e) -> {
            setUsername(uTextField.getText());
            setPassword(new String(pwdField.getPassword()));

            try {
                this.backupDatabase(DEFAULT_URL, user, password);
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(getComponentPopupMenu(), "Backup Failed!\nError: " + ex.getMessage());
                System.out.println("Connection Failed: " + ex.getMessage());}
        });
    }
}
