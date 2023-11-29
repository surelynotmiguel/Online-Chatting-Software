package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;

public class AboutDialog extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AboutDialog(JFrame parent) {
        super(parent, "About", true);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new FlowLayout());
        JLabel label = new JLabel("<html>" + GlobalConstants.getNameVersion() + "<br><br>" 
        						  + "<center>Created by:</center><br>" + GlobalConstants.getAuthorsWithImages()
        						  + "<center>Last modified:  " + GlobalConstants.getLastModifiedDate() + "</center></html>");
        label.setBorder(new EmptyBorder(10, 20, 20, 20));
        add(label);
        pack();
        setLocationRelativeTo(null);
    }
}
