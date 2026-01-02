package ui;

import db.OpenDatabaseTable;
import resources.JPanelPrinter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//import java.nio.file.WatchEvent;
//import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
//import javax.swing.JTable;


public class VisualGUI {
	private static JFrame mainInventoryFrame;
	private static JPanel inventoryTablePanel;
	private FooterPanel footerPanel;
	// private static JMenuBar menuBar;
	// private static JMenu fileMenu;
	// private static JMenu editMenu;
	// private static JMenu helpMenu;
	// private static JTable inventoryTable;
	// private static JPanel mainPanel;

    public VisualGUI() {
        // Constructor
    } 

    //MAIN
    public static void main(String[] args) {
        // Application entry point
        VisualGUI main = new VisualGUI();
        main.mainFrame();
		main.panelsSetup();
        main.showMainMenu();
        main.fileMenu();
        main.editMenu();
        main.helpMenu();
    }

	//Frame Constructor
	void mainFrame() {
		//set Layout
		mainInventoryFrame = new JFrame();	
		mainInventoryFrame.setLayout(new BorderLayout());

		/*We will let fonts and size to be controlled by manager for now */
		//Initialize Frame
		mainInventoryFrame = new JFrame("Inventory System"); //Frame Title
		mainInventoryFrame.setSize(800, 800); // Set initial frame size
		mainInventoryFrame.setResizable(true); // Set frame to be resizable (true)
		mainInventoryFrame.setVisible(true);//Make JFrame visible
		/* Background colors and all color options will need to be eventually set to variable to be able to controlled by end user */

		//JFrame to exit on close
		mainInventoryFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {System.exit(0);}
		});

		
	}

    // MENU BAR Constructor
    void showMainMenu(){
        final JMenuBar mainMenuBar = new JMenuBar();

        //Add Menu Tabs to Menu Bar
        mainMenuBar.add(fileMenu());
        mainMenuBar.add(editMenu());
        mainMenuBar.add(helpMenu());

        //MENU bar settings
        mainMenuBar.setBackground(Color.WHITE);
        mainMenuBar.setBorderPainted(false);
        mainMenuBar.setOpaque(true);

        
        //Set MENU bar to JFrame
        mainInventoryFrame.setJMenuBar(mainMenuBar);
		mainInventoryFrame.setVisible(true);
    
    }

	void panelsSetup() {
		//Add Panels to Frame
		JPanel mainPanel = new JPanel();
		inventoryTablePanel = new JPanel();
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		FooterPanel footerPanel = new FooterPanel();

		 // Set panel background colors and sizes
		
		mainPanel.setBackground(Color.LIGHT_GRAY);
		inventoryTablePanel.setBackground(Color.WHITE);
		leftPanel.setBackground(Color.LIGHT_GRAY);
		rightPanel.setBackground(Color.LIGHT_GRAY);
		//footerPanel.setBackground(Color.GRAY); /*FooterPanel is handled separately in ui/FooterPanel.java */

		 // Set panel preferred sizes

		mainPanel.setPreferredSize(new Dimension(0, 20));//Header Panel
		inventoryTablePanel.setPreferredSize(new Dimension(700, 600));
		leftPanel.setPreferredSize(new Dimension(20, 0));
		rightPanel.setPreferredSize(new Dimension(20, 0));
		//footerPanel.setPreferredSize(new Dimension(0, 20));

		 // Add panels to the frame's content pane with BorderLayout positions

		mainInventoryFrame.getContentPane().add(mainPanel, BorderLayout.NORTH);
		mainInventoryFrame.getContentPane().add(inventoryTablePanel, BorderLayout.CENTER);
		mainInventoryFrame.getContentPane().add(leftPanel, BorderLayout.WEST);
		mainInventoryFrame.getContentPane().add(rightPanel, BorderLayout.EAST);
		mainInventoryFrame.getContentPane().add(footerPanel, BorderLayout.SOUTH);
	}

    //FILE menu items function
	private JMenu fileMenu() {
		//------------FILE MENU ITEMS----------------

		JMenu fileMenu = new JMenu("FILE");

		//Open Database menu item
		JMenuItem openDataBase = new JMenuItem("Open Database");
		openDataBase.setActionCommand("openDB");

		//Backup Database menu item
		JMenuItem backupDataBase = new JMenuItem("Backup Database");
		backupDataBase.setActionCommand("backupDB");

		//Print Menu item to print current frame
		JMenuItem printItem = new JMenuItem("Print");
		printItem.setActionCommand("print");

		//Exit menu item with action command "exit"
		JMenuItem exitInventoryProgram = new JMenuItem("Exit");
		exitInventoryProgram.setActionCommand("exit");

		//Add action listeners to file menu items
		openDataBase.addActionListener(new openDataBaseListener());
		backupDataBase.addActionListener(new backupDataBaseListener());
		printItem.addActionListener(new printItemListener());
		exitInventoryProgram.addActionListener(new exitInventoryProgramListener());


		// Add menu items to the FILE menu
		fileMenu.add(openDataBase);
		fileMenu.add(backupDataBase);
		fileMenu.add(printItem);
		fileMenu.add(exitInventoryProgram);

		return fileMenu;
		
	}

    //EDIT menu items function
	private JMenu editMenu() {
		//------------EDIT MENU ITEMS----------------

		JMenu editMenu = new JMenu("EDIT");

		//EDIT items
		JMenuItem addItem = new JMenuItem("Add Item");
		addItem.setActionCommand("addItem");

		//Delete items
		JMenuItem deleteItem = new JMenuItem("Delete Item");
		deleteItem.setActionCommand("deleteItem");

		//Modify items
		JMenuItem modifyItem = new JMenuItem("Modify Item");
		modifyItem.setActionCommand("modifyItem");

		//Add action listeners to EDIT menu items
		addItem.addActionListener(new addItemListener());
		deleteItem.addActionListener(new deleteItemListener());
		modifyItem.addActionListener(new modifyItemListener());	
		
		// Add menu items to the EDIT menu
		editMenu.add(addItem);
		editMenu.add(deleteItem);
		editMenu.add(modifyItem);
	
		return editMenu;
	}

    //HELP menu items function returns HELP menu object	
	private JMenu helpMenu() {
		
		JMenu helpMenu = new JMenu("HELP");

		//About Program menu item
		JMenuItem aboutProgram = new JMenuItem("About Program");
		aboutProgram.setActionCommand("aboutProgram");

		//Contact Support menu item
		JMenuItem contactSupport = new JMenuItem("Contact Support");
		contactSupport.setActionCommand("contactSupport");

		//Check for Updates menu item
		JMenuItem checkForUpdates = new JMenuItem("Check for Updates");
		checkForUpdates.setActionCommand("checkForUpdates");

		//Add action listeners to HELP menu items
		aboutProgram.addActionListener(new aboutProgramListener());
		contactSupport.addActionListener(new contactSupportListener());
		checkForUpdates.addActionListener(new checkForUpdatesListener());

		// Add menu items to the HELP menu
		helpMenu.add(aboutProgram);
		helpMenu.add(contactSupport);
		helpMenu.add(checkForUpdates);

		return helpMenu;
	}

    //----------------------Listeners--------------------------------------
    //Action Listeners for menu items

	//File Menu Action Listeners------------------------
	class openDataBaseListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			System.out.println("Open Database Clicked");
			//database_connect.main(null);
			//OpenDatabaseTable openDatabaseTable = new OpenDatabaseTable();
			OpenDatabaseTable openDBTable = new OpenDatabaseTable();
			//JPanel openDatabaseTable = openDBTable.getDatabaseTablePanel();
			
			//Remove existing inventory table panel from frame
			mainInventoryFrame.getContentPane().remove(inventoryTablePanel);
			//mainInventoryFrame.getContentPane().add(openDatabaseTable, BorderLayout.CENTER);
			inventoryTablePanel = openDBTable.getDatabaseTablePanel();
			//Re-add updated panel to frame
			mainInventoryFrame.getContentPane().add(inventoryTablePanel, BorderLayout.CENTER);
			//Refresh frame to show updated panel
			mainInventoryFrame.revalidate();
			//Repaint frame
			mainInventoryFrame.repaint();
		}
	}
	class backupDataBaseListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			System.out.println("Backup Database Clicked");
			
		}
	}
	class printItemListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			System.out.println("Print Clicked");
			
			//Invoke JPanelPrinter to print the inventoryTablePanel
			JPanelPrinter panelPrinter = new JPanelPrinter(inventoryTablePanel, "Inventory Report", "Page 1");
			//Call print method
			panelPrinter.printPanel();
		}
	}
	class exitInventoryProgramListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			System.out.println("Exit Clicked");
			System.exit(0);
		}
	}


	//Edit Menu Action Listeners------------------------
	class addItemListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Add Item Clicked");
		}
	}
	class deleteItemListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Delete Item Clicked");
		}
	}
	class modifyItemListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Modify Item Clicked");
		}
	}
	//Help Menu Action Listeners------------------------
	class aboutProgramListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("About Program Clicked");
		}
	}
	class contactSupportListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Contact Support Clicked");
		}
	}
	class checkForUpdatesListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Check for Updates Clicked");
		}
	}

	//General Menu Item Listener for Exit Command--------------------
	class MenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String menuName = event.getActionCommand();
			if (menuName.equals("exit")) {
                // exit program if "EXIT" menu item is clicked
				System.exit(0); 
			}
		}
	}

	/*-------------------------------Methods----------------------------------*/
	public FooterPanel getFooterPanel() {return footerPanel;}
	public void refreshFooterPanel() {
		if (footerPanel != null) {
			footerPanel.revalidate();
			footerPanel.repaint();
		}
	}

}