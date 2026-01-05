package app;

import db.OSIConnect;
import ui.OSIFrame;
import ui.OSIMenu;

public class OSI {
    

    public static void main(String[] args) {
        System.out.println("OSI Open");//Testing purpose ONLY!!!
        //Exception handling for the main application launch
        try {
        //Use SwingUtilities to ensure thread safety when creating the GUI
        //Launch the GUI on the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> {

            //Initialize database service
            OSIConnect dbService = new OSIConnect();
            
            //Create and show the main application frame
            OSIFrame mainFrame = new OSIFrame("OSI Inventory System", dbService);
            mainFrame.setVisible(true);

            //Initialize menu bar
            OSIMenu menuBar = new OSIMenu(mainFrame, dbService);
            mainFrame.setJMenuBar(menuBar);
            

            });
        }
    
        catch (Exception e) {
            System.out.println("Error in OSI.java");
            e.printStackTrace();
        }
    }
}
