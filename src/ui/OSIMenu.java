package ui;

import resources.JPanelPrinter;

import java.awt.Color;
import javax.swing.JDialog;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import db.DatabaseBackup;

public class OSIMenu extends JMenuBar {
    
    public OSIMenu(OSIFrame frame, db.OSIConnect dbService) {
        super();
        System.out.println("OSIMenu Open");
        // Set menu properties
        this.setBackground(Color.LIGHT_GRAY);
        this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.DARK_GRAY));
        this.setOpaque(true);

//---------------Menu Items----------------//
        // Create "File" menu
        JMenu fileMenu = new JMenu("File");
        // Create Help menu
        JMenu helpMenu = new JMenu("Help");


//---------------File Menu Items----------------//

        // Create "Connect to Database" menu item
        JMenuItem connectItem = new JMenuItem("Connect to Database");

        // Create "Backup" menu item
        JMenuItem backupItem = new JMenuItem("Backup");

        // Create Print menu item
        JMenuItem printItem = new JMenuItem("Print");
        
        // Create "Exit" menu item
        JMenuItem exitItem = new JMenuItem("Exit");

//---------------Actions Listeners----------------//

    // Add action Listener to "Print" menu item
        printItem.addActionListener(e -> {
            System.out.println("Print Clicked");

            /*
            * Using Component Tree Traversal to find the inventory table panel within the main frame.
            * This assumes the inventory table panel is a direct child of the frame's content pane.
             */

            // Get the Window (OSIFrame) ancestor of this menu
            OSIFrame osiFrame = (OSIFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
            // Check if osiFrame is indeed an instance of OSIFrame
            if (osiFrame instanceof OSIFrame) {
                osiFrame = (OSIFrame) osiFrame;
                // Obtain reference to the inventory table panel from the main frame
                javax.swing.JPanel panelToPrint = osiFrame.getCentralPanel();
                // Invoke JPanelPrinter from package.resources
                JPanelPrinter panelPrinter = new JPanelPrinter(panelToPrint, "Inventory Report", "Page 1");
                //Call print method 
                panelPrinter.printPanel();
            }
        });         
        
    // Add action Listener to "Backup" menu item
        backupItem.addActionListener(e -> {
            DatabaseBackup bdBup = new DatabaseBackup();
            bdBup.backConn();
        });

    // Add actions Listener to "Exit" menu item
        exitItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

    // Add action Listener to "Connect to Database" menu item
        connectItem.addActionListener(e -> {
            JDialog connectDialog = new JDialog(frame, "Database Connection", true);
            connectDialog.setSize(400, 300);
            connectDialog.setLocationRelativeTo(frame);
            connectDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            connectDialog.setVisible(true);
        });



        // Add menu items to "File" menu
        fileMenu.add(connectItem);
        fileMenu.add(backupItem);
        fileMenu.add(printItem);
        fileMenu.add(exitItem);

        // Add menus to the menu bar
        this.add(fileMenu);
        this.add(helpMenu);
    }
}
