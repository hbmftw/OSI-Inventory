package ui;

import resources.JPanelPrinter;

import java.awt.Color;
import java.util.Objects;

import javax.swing.JDialog;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import db.DatabaseBackup;
import db.OpenDataPanel;

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
                // Close DB connection
                System.out.println("Closing DB!");
                try {
                    // Check if DB is open & if so close
                    if (dbService != null && !dbService.isClosed()) {
                        dbService.close();
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                // Close Application
                System.exit(0);
            }
        });

    // Add action Listener to "Connect to Database" menu item
        connectItem.addActionListener(e -> {
            // Create and show a new login window
            JDialog dlg = new JDialog(frame, "OSI Login", true);
            dlg.setSize(300, 200);
            dlg.setLocationRelativeTo(frame);
            OpenDataPanel pnl = new OpenDataPanel(dbService, () -> {
                //not making it to the next println from here
                System.out.println(Objects.toString(frame, "No ConnectionFound"));
                if (frame != null && frame.getFooterPanel() != null) {
                    frame.getFooterPanel().updateStatus(dbService.getDbUser());
                    System.out.println("if Statement Refresh Footer");
                }
            });
            dlg.getContentPane().add(pnl);
            dlg.setSize(300, 200);
            dlg.setLocationRelativeTo(frame);
            dlg.setModal(true);
            dlg.setVisible(true);
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
