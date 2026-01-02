package ui;

import java.awt.Dimension;

public class FooterPanel extends javax.swing.JPanel {
    public String dbStatus = "Disconnected";
    private javax.swing.JLabel statusLabel;

    //Constructor for FooterPanel
    public FooterPanel() {
        super();
        this.setBackground(java.awt.Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(0, 20));
        statusLabel = new javax.swing.JLabel("Database Status: " + dbStatus);
        this.add(statusLabel);
        this.setVisible(true);
    }

    //Method to update the database status label
    public void updateStatus(String newStatus) {
        //Ensure dbStatus is not null or empty before updating
        if (this.dbStatus == null || this.dbStatus.isEmpty()) {
            this.dbStatus = "Disconnected";
        } 
        //Update only if newStatus is valid
        if (newStatus != null && !newStatus.isEmpty()) {
            this.dbStatus = newStatus;
        }
    }
}