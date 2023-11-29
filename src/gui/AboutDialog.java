package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;

/**
 * The AboutDialog class represents the dialog box displaying information about the application.
 */
public class AboutDialog extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Constructs an AboutDialog object.
     *
     * @param parent The parent JFrame
     */
	public AboutDialog(JFrame parent) {
        super(parent, "About", true);
        initializeUI();
    }

    /**
     * Initializes the UI components and layout for the AboutDialog.
     */
    private void initializeUI() {
        setLayout(new FlowLayout());
        JLabel label = new JLabel("<html><center>" + GlobalConstants.getNameVersion() + "</center><br>" 
        						  + "<center>Created by:</center><br>" + GlobalConstants.getAuthorsWithImages()
        						  + "<center>Last modified:  " + GlobalConstants.getLastModifiedDate() + "</center></html>");
        label.setBorder(new EmptyBorder(10, 20, 20, 20));
        add(label);
        pack();
        setLocationRelativeTo(null);
    }
}
