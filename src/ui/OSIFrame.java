package ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.BorderLayout;

public class OSIFrame extends JFrame {

    public String dbStatus = "DB Status: Not Connected";
    public String getDbStatus() {return dbStatus;}

    private FooterPanel footerPanel;


    public OSIFrame(String title) {
        // Initialize the JFrame with a title
        super("OSI Model Visualization");

        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Set an icon for the frame (assuming the image is in the resources folder)
        ImageIcon icon = new ImageIcon("resources/OSI.png");
        this.setIconImage(icon.getImage());

        //initialize Panels

        
        //footerPanel = new FooterPanel();
        this.add(new HeaderPanel(), BorderLayout.NORTH);
        //this.add(new NSPanel(), BorderLayout.SOUTH);
        this.add(new SidePanel(), BorderLayout.EAST);
        this.add(new CentralPanel(), BorderLayout.CENTER);
        //Add West Panel last to ensure it is on top if overlapping occurs
        this.add(new SidePanel(), BorderLayout.WEST);
        this.add(footerPanel, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null); // Center the frame on the screen
        this.setVisible(rootPaneCheckingEnabled);

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