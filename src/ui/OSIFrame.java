package ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import db.OSIConnect;

import java.awt.BorderLayout;
import java.sql.SQLException;

public class OSIFrame extends JFrame {
    
 

    private FooterPanel footerPanel;


    public OSIFrame(String title, db.OSIConnect dbService) {
        // Initialize the JFrame with a title
        super("OSI Model Visualization");

        System.out.println("OSIFrame Open");//Testing purpose ONLY!!!

        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Set an icon for the frame (assuming the image is in the resources folder)
        ImageIcon icon = new ImageIcon("resources/OSI.png");
        this.setIconImage(icon.getImage());

        //initialize Panels
//TODO: Need to change EAST panel to a scroll bar!!!!!!!!!!!! POPUP windows need to locked to FRAME!!!!
        
        //footerPanel = new FooterPanel();
        this.add(new HeaderPanel(), BorderLayout.NORTH);
        //this.add(new NSPanel(), BorderLayout.SOUTH);
        this.add(new SidePanel(), BorderLayout.EAST);
        this.add(new CentralPanel(), BorderLayout.CENTER);
        //Add West Panel last to ensure it is on top if overlapping occurs
        this.add(new SidePanel(), BorderLayout.WEST);
        this.add(new FooterPanel(), BorderLayout.SOUTH);

        this.setLocationRelativeTo(null); // Center the frame on the screen
        this.setVisible(rootPaneCheckingEnabled);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // Close DB connection
                System.out.println("Closing DB!");
                try {
                    // Check if DB is open & if so close
                    if (dbService != null && !dbService.isClosed()) {
                        dbService.close();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                // Close Application
                System.exit(0);
            }
        });

    }

    public FooterPanel getFooterPanel() {return footerPanel;}
    public void refreshFooterPanel() {
        if (footerPanel != null) {
            footerPanel.revalidate();
            footerPanel.repaint();
        }
    }   

    public CentralPanel getCentralPanel() {
        return (CentralPanel) this.getContentPane().getComponent(2); // Assuming CentralPanel is the third added component
    }
    public void refreshCenterPanel() {
        CentralPanel centralPanel = getCentralPanel();
        if (centralPanel != null) {
            centralPanel.revalidate();
            centralPanel.repaint();
        }
    }
}