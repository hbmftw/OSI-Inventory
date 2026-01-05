package db;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class OpenDataPanel extends JPanel {

    //Database credentials
    private String user = "postgres";
    private String password = "postgres";
    private final Runnable onLogin;
    private final db.OSIConnect conn;

    //Default constructor
    public OpenDataPanel() { this(new db.OSIConnect(), null); }

    //Constructor with parameters
    public OpenDataPanel(db.OSIConnect conn, Runnable onLogin) {
        super();
        this.conn = conn == null ? new db.OSIConnect() : conn;
        this.onLogin = onLogin;
        dbConnect();
    }

    //Getters and Setters for username and password
    private void setUsername(String username) {this.user = username;}
    private void setPassword(String pwd) {this.password = pwd;}
    private String getUsername() {return this.user;}
    private String getPassword() {return this.password;}

    //Method to create the DB connection panel
    private void dbConnect() {
        //Set layout and background color
        this.setLayout(new FlowLayout());
        this.setBackground(Color.LIGHT_GRAY);

        //Create and add components (username field)
        JLabel userName = new JLabel("Username:");
        this.add(userName);
        JTextField userNameField = new JTextField(20);
        this.add(userNameField);

        //Password field
        JLabel pwdLabel = new JLabel("Password:");
        this.add(pwdLabel);
        JPasswordField pwdField = new JPasswordField(20);
        this.add(pwdField);

        //Login button
        javax.swing.JButton loginBtn = new javax.swing.JButton("Login");
        this.add(loginBtn);

        //Set visibility
        this.setVisible(getFocusTraversalKeysEnabled());

        //Add action listener for login button
        loginBtn.addActionListener((ActionEvent e) -> {
            setUsername(userNameField.getText());
            setPassword(new String(pwdField.getPassword()));
            conn.setCredentials(getUsername(), getPassword());
            //Attempt to connect to the database
            try {
                conn.getConnection();
                if (conn != null){
                    try {conn.close();}catch (Exception se){se.printStackTrace();}
                    
                }
                SwingUtilities.invokeLater(() -> {
                    if (onLogin != null) onLogin.run();
                    SwingUtilities.getWindowAncestor(OpenDataPanel.this).dispose();
                });
                System.out.println("dbConnect() Finish connection!");
            } catch (Exception ex) {
                System.out.println("Connection failed: " + ex.getMessage());
            }
        });
    }

    //Abstract class for login action listener
    abstract class LoginActionListener implements java.awt.event.ActionListener {
        @Override public abstract void actionPerformed(ActionEvent e);
    }
    
}
