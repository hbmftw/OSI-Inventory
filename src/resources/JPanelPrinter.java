package resources;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JPanel;

public class JPanelPrinter implements Printable {

    private final JPanel panelToPrint;
    private final String headerText;
    private final String footerText;

    public JPanelPrinter(JPanel panel, String header, String footer) {
        this.panelToPrint = panel;
        this.headerText = header;
        this.footerText = footer;
    }   

    @Override
    //Method is called by the printing system to render each page
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        // Cast Graphics to Graphics2D for advanced operations
        Graphics2D g2d = (Graphics2D) g;
        // Translate to the printable area
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        // Print header
        g2d.setFont(new Font("Serif", Font.BOLD, 14));
        g2d.drawString(headerText, 100, 50);

        // Print footer
        g2d.setFont(new Font("Serif", Font.PLAIN, 12));
        g2d.drawString(footerText, 100, (int) pf.getImageableHeight() - 10);

        //Translate for panel content
        g2d.translate(0, 30); // Adjust Y position to account for header

        //Scale panel to fit page if necessary
        double panelWidth = panelToPrint.getWidth();
        double panelHeight = panelToPrint.getHeight();
        double pageWidth = pf.getImageableWidth();
        double pageHeight = pf.getImageableHeight() - 60; // Adjust for header and footer
        double scaleX = pageWidth / panelWidth;
        double scaleY = pageHeight / panelHeight;
        double scale = Math.min(scaleX, scaleY);
        g2d.scale(scale, scale);



        // Print the panel
        panelToPrint.printAll(g2d);

        return PAGE_EXISTS;

    }

    public void printPanel() {
        // This method can be used to initiate printing if needed
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }
    
}   